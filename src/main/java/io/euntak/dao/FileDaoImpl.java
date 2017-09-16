package io.euntak.dao;

import io.euntak.domain.FileInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FileDaoImpl implements FileDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<FileInfo> rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class);

    public FileDaoImpl(DataSource dataSource) {

        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("file").usingGeneratedKeyColumns("id");
    }

    @Override
    public FileInfo selectFileById(Long fileId) {

        Map<String, ?> params = Collections.singletonMap("fileId", fileId);

        try {
            return jdbc.queryForObject(FileSqls.SELECT_FILE_BY_ID, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int updateFileById(Long fileId, FileInfo fileInfo) {

        Map<String, Object> params = new HashMap<>();

        params.put("fileName", fileInfo.getFileName());
        params.put("saveFileName", fileInfo.getSaveFileName());
        params.put("fileLength", fileInfo.getFileLength());
        params.put("contentType", fileInfo.getContentType());
        params.put("modifyDate", fileInfo.getModifyDate());
        params.put("fileId", fileId);

        return jdbc.update(FileSqls.UPDATE_FILE_BY_ID, params);
    }

    @Override
    public Long insert(FileInfo f) {

        SqlParameterSource params = new BeanPropertySqlParameterSource(f);
        return insertAction.executeAndReturnKey(params).longValue();
    }

    @Override
    public int delete(Long fileId) {

        Map<String, Object> params = new HashMap<>();
        params.put("fileId", fileId);
        return jdbc.update(FileSqls.DELETE_FILE_BY_ID, params);
    }

    @Override
    public List<FileInfo> selectAllFiles() {

        Map<String, Object> params = Collections.emptyMap();
        return jdbc.query(FileSqls.SELECT_ALL_FILES, params, rowMapper);
    }
}
