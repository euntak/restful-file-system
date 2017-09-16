package io.euntak.dao;

public class FileSqls {
    final static String SELECT_FILE_BY_ID =
            "SELECT"
                    + " id, file_name, save_file_name, file_length, content_type"
                    + " FROM file "
                    + " WHERE id = :fileId "
                    + " AND delete_flag = 0 ";

    final static String SELECT_ALL_FILES =
            "SELECT"
                    + " id, file_name, save_file_name, file_length, content_type"
                    + " FROM file "
                    + " WHERE delete_flag = 0 ";

    final static String UPDATE_FILE_BY_ID =
            "UPDATE file"
                    + " SET file_name = :fileName, save_file_name = :saveFileName, "
                    + " file_length = :fileLength, content_type = :contentType, "
                    + " modify_date = :modifyDate "
                    + " WHERE delete_flag = 0 "
                    + " AND id = :fileId";

    final static String DELETE_FILE_BY_ID =
            "DELETE FROM file WHERE id = :fileId";
}
