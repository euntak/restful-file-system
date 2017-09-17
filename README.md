# restful-file-system
restul-file-system

## File Sql (Mysql)

```sql
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `save_file_name` varchar(4000) NOT NULL,
  `file_length` int(11) NOT NULL,
  `content_type` varchar(255) NOT NULL,
  `delete_flag` int(1) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
```