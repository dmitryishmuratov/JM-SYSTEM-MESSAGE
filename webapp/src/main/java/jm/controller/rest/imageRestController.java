package jm.controller.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/images")
@Tag(name = "image", description = "Image API")
public class imageRestController {

    @GetMapping(value = "/{userId}/{imageName}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: get user image"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND: unable to find user image")
    })
    public ResponseEntity<?> getUserImage(@PathVariable String userId, @PathVariable String imageName) throws IOException {
        try {
            InputStream in = getClass().getResourceAsStream("/static/image/" + userId + "/" + imageName);
            String[] nameAsArray = imageName.split("\\.");
            MediaType mediaType = MediaType.valueOf("image/" + nameAsArray[nameAsArray.length - 1]);
            return ResponseEntity.ok().contentType(mediaType).body(IOUtils.toByteArray(in));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
