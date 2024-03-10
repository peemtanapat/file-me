package com.peemtanapat.fileme.fileservice.service;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class FileS3ServiceTest {

    @InjectMocks
    private FileS3Service fileS3Service;
    @Mock
    private AmazonS3 amazonS3;
    @Mock
    private JWTService jwtService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFile() {

        String username = "tanapat.pm@gmail.com";
        MockMultipartFile file =
                new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());

        fileS3Service.uploadFile(file, username);
    }

}
