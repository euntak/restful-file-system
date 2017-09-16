package io.euntak.dao;

import io.euntak.domain.FileInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class FileDaoImpl implements FileDao{
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<FileInfo> rowMapper= BeanPropertyRowMapper.newInstance(FileInfo.class);

    public FileDaoImpl(DataSource dataSource){
        this.jdbc=new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("file_copy").usingGeneratedKeyColumns("id");
    }

    @Override
    public FileInfo selectFileById(Long fileId) {
        return null;
    }

    @Override
    public Long insert(FileInfo f) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(f);
        return insertAction.executeAndReturnKey(params).longValue();
    }
}
