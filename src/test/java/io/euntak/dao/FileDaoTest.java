package io.euntak.dao;

import io.euntak.config.RootApplicationContextConfig;
import io.euntak.domain.FileInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith (SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration (classes = RootApplicationContextConfig.class)
@Transactional
public class FileDaoTest {
    private static final Logger log = LoggerFactory.getLogger(FileDaoTest.class);

    @Autowired
    FileDao fileDao;

    @Test
    public void shouldSelectAllFile() {

        List<FileInfo> fileInfoList = fileDao.selectAllFiles();

        assertNotNull(fileInfoList);

        fileInfoList.forEach(file -> {
           log.info("fileInfo Id : " + file.getId());

           FileInfo fileInfo = fileDao.selectFileById(file.getId());
           assertThat(file.getId(), is(fileInfo.getId()));
        });
    }

}
