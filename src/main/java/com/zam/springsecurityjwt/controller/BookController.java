package com.zam.springsecurityjwt.controller;

import com.zam.springsecurityjwt.dto.BookDTO;
import com.zam.springsecurityjwt.dto.BookResponse;
import com.zam.springsecurityjwt.dto.BookUpdateDto;
import com.zam.springsecurityjwt.dto.KeyDTO;
import com.zam.springsecurityjwt.entity.Book;
import com.zam.springsecurityjwt.entity.Category;
import com.zam.springsecurityjwt.exeptions.ApiRequestException;
import com.zam.springsecurityjwt.exeptions.FileUploadExceptions;
import com.zam.springsecurityjwt.repo.CategoryRepository;
import com.zam.springsecurityjwt.service.FileStorageService;
import com.zam.springsecurityjwt.service.impl.BookServiceImpl;

import com.zam.springsecurityjwt.util.Helper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController()
@RequestMapping("/api/v1/book")
@SecurityRequirement(name = "javainuseapi")
@ControllerAdvice
public class BookController {
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/all")
    public ResponseEntity<Object> findAll(){
        List<BookResponse> bookResponseList = bookService.findAll();
        if(bookResponseList.size() == 0){
            return Helper.generateResponse(
                    "empty book" , HttpStatus.NOT_FOUND , bookResponseList
            );
        }
        return Helper.generateResponse("there are book in database" , HttpStatus.OK , bookResponseList);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addBook(@ModelAttribute BookDTO bookDTO) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(bookDTO.getCategory());
            if (categoryOptional.isPresent()) {
                String saveFile = fileStorageService.saveFile(bookDTO.getFile());
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/v1/book/download/")
                        .path(saveFile)
                        .toUriString();
                var book = Book.builder()
                        .bookName(bookDTO.getBookName())
                        .price(bookDTO.getPrice())
                        .pages(bookDTO.getPages())
                        .author(bookDTO.getAuthor())
                        .description(bookDTO.getDescription())
                        .category(categoryOptional.get())
                        .coverImage(saveFile)
                        .build();
                Optional<Book> optional = bookService.addBook(book);
                System.out.println(fileDownloadUri);
                return optional.<ResponseEntity<Object>>map(ResponseEntity::ok)
                        .orElseGet(() -> Helper.generateResponse("failed to add book , there same book name", HttpStatus.BAD_REQUEST, null));
            } else {
                throw new ApiRequestException("category not found");
            }
        } catch (ApiRequestException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
    @PostMapping("/edit")
    public ResponseEntity<Object> updateBook(@RequestBody BookUpdateDto bookUpdateDto){
      try {
          Optional<Book> updateBook = bookService.updateBook(bookUpdateDto);
          return updateBook.map(book -> Helper.generateResponse(
                  "success update book", HttpStatus.OK, book
          )).orElseGet(() -> Helper.generateResponse(
                  "Failed to update book , book not found", HttpStatus.NOT_FOUND, null
          ));
      }catch (RuntimeException e){
          throw new ApiRequestException(e.getCause().getMessage());
      }
    }
    @GetMapping("/load/{path}")
    public ResponseEntity<Object> getImageUri(@PathVariable("path") String fileName) throws IOException {
    try {
        Resource resource = fileStorageService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }catch (RuntimeException e){
        throw new ApiRequestException(e.getMessage());
    }
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Object> downloadFile(@PathVariable("fileName") String fileName){
        Resource resource = fileStorageService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // mapping for get image and show to view browser
    @GetMapping("/image/{id}")
    public ResponseEntity<Object> showImage(@PathVariable("id") Integer id) throws IOException {
        Optional<Book> bookOptional = bookService.findById(id);
        if(bookOptional.isPresent()){
            if(bookOptional.get().getCoverImage() == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                Resource resource = fileStorageService.loadFile(bookOptional.get().getCoverImage());
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(bytes);
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteById(@RequestBody KeyDTO<Integer> id){
        boolean byId = bookService.deleteById(id.getKey());
        return byId ? Helper.generateResponse(
                "Success Delete book" , HttpStatus.OK , null )
                :  Helper.generateResponse("failed delete book" , HttpStatus.NOT_FOUND
                , null);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") Integer id){
        return bookService.findById(id)
                .map(book -> Helper.generateResponse("book found it", HttpStatus.OK, book))
                .orElse(Helper.generateResponse("book with id " + id + " not found", HttpStatus.NOT_FOUND, null));
    }

    // exception handler
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleFileUploadError(){
        return Helper.generateResponse("failed to upload file file size must be under 5 mb" , HttpStatus.BAD_REQUEST , null);
    }

}
