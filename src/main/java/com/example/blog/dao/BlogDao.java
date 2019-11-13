package com.example.blog.dao;

import com.example.blog.model.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {

    private final SqlSession sqlSession;

    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> selectBlog(Integer page, Integer pageSize, Integer userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("offset", (page - 1) * pageSize);
        map.put("limit", pageSize);
        final List<Blog> blog = sqlSession.selectList("selectBlog", map);
        return blog;
    }

    public Blog selectBlogById(int blogId) {
        Blog blog = sqlSession.selectOne("selectBlogById", blogId);
        return blog;
    }

    public void deleteBlog(Integer blogId) {
        sqlSession.delete("deleteBlog", blogId);
    }

    public Blog updateBlog(Blog targetBlog) {
        sqlSession.update("updateBlog", targetBlog);
        return selectBlogById(targetBlog.getId());
    }


    public int countBlog(Integer userId) {
        return sqlSession.selectOne("countBlog", userId);
    }

    public Blog insertBlog(Blog blog) {
        sqlSession.insert("insertBlog", blog);
        return selectBlogById(blog.getId());
    }


}
