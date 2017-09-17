package io.euntak.contoller;

import io.euntak.config.RootApplicationContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith (SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration (classes = RootApplicationContextConfig.class)
@Transactional
public class FileRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void shouldFileUpload() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile("file", "orig", "image/jpg", "bar".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/files")
                .file(mockFile)).andExpect(status().is(200));
    }
}
