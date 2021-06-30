package iducs.springboot.blog201712015.repository;

import iducs.springboot.blog201712015.domain.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class BlogRepositoryImpl implements BlogRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BlogRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int create(Blog blog) {
        String query = "insert into b201712015(id, title, content, filepath, b201712015ger) values(seq_b201712015.nextval, ?, ?, ?, ?)";
        return jdbcTemplate.update(query, blog.getTitle(), blog.getContent(), blog.getFilepath(), blog.getB201712015ger());
    }

    @Override
    public Optional<Blog> readById(Long id) {
        String query = "select * from b201712015 where id = ?";
        List<Blog> result = jdbcTemplate.query(query,new Object[] {id}, blogRowMapper());
        return result.stream().findAny();
    }

    @Override
    public List<Blog> readByTitle(String title) {
        Object[] params = {"%"+title+"%"};
        String query = "select * from b201712015 where title like ?";
        return jdbcTemplate.query(query, params, blogRowMapper());
    }

    @Override
    public List<Blog> readByMember(String name) {
        Object[] params = {"%"+name+"%"};
        String query = "select * from b201712015 where b201712015ger like ?";
        return jdbcTemplate.query(query, params, blogRowMapper());
    }

    @Override
    public List<Blog> readBlogs() {
        return jdbcTemplate.query("select * from b201712015", blogRowMapper());
    }

    @Override
    public int update(Blog blog) {
        String query = "update b201712015 set title = ?, content = ?, filepath = ?, b201712015ger = ? where id = ?";
        return jdbcTemplate.update(query, blog.getTitle(), blog.getContent(), blog.getFilepath(), blog.getB201712015ger(), blog.getId());
    }

    @Override
    public int delete(Long id) {
        String query = "delete from b201712015 where id = ?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public int rowCnt() {
        String query = "select count(*) from b201712015";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    @Override
    public int rowCntTitle(String title) {
        Object[] params = {"%"+title+"%"};
        String query = "select count(*) from b201712015 where title like ?";
        return jdbcTemplate.queryForObject(query, params, Integer.class);
    }

    @Override
    public int rowCntName(String name) {
        Object[] params = {"%"+name+"%"};
        String query = "select count(*) from b201712015 where b201712015ger like ?";
        return jdbcTemplate.queryForObject(query, params,Integer.class);
    }

    private RowMapper<Blog> blogRowMapper(){
        return (rs, rowNum) -> {
            Blog blog = new Blog();
            blog.setId(rs.getLong("id"));
            blog.setTitle(rs.getString("title"));
            blog.setContent(rs.getString("content"));
            blog.setFilepath(rs.getString("filepath"));
            blog.setB201712015ger(rs.getString("b201712015ger"));
            blog.setReg_date_time(rs.getString("reg_date_time"));
            return blog;
        };
    }
}
