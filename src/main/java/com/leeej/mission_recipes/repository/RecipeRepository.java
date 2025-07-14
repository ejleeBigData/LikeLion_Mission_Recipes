package com.leeej.mission_recipes.repository;

import com.leeej.mission_recipes.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecipeRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Recipe> recipeRowMapper = (resultSet, rowNum) ->
            Recipe.builder()
                    .id(resultSet.getInt("id"))
                    .title(resultSet.getString("title"))
                    .description(resultSet.getString("description"))
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .build();

    public List<Recipe> findAll() {
        String sql = " SELECT id, title, description, created_at AT TIME ZONE 'Asia/Seoul' AS created_at  " +
                " FROM recipes Order by title ";
        return jdbcTemplate.query(sql, recipeRowMapper) ;
    }

    public Recipe findById(int id) {
        String sql = " SELECT  id, title, description, created_at AT TIME ZONE 'Asia/Seoul' AS created_at FROM recipes WHERE id = ? ";

        return jdbcTemplate.queryForObject(sql, recipeRowMapper, id );
    }

    public int save(Recipe recipe) {
        String sql = " INSERT INTO recipes(title, description) VALUES(?, ?) ";

        return jdbcTemplate.update(sql, recipe.getTitle(), recipe.getDescription());
    }

    public int update(Recipe recipe) {
        String sql = " UPDATE recipes SET title = ? , "  +
                " description = ? WHERE id = ? ";

        return jdbcTemplate.update(sql, recipe.getTitle(), recipe.getDescription(), recipe.getId());
    }

    public int delete(Integer id) {
        String sql = " DELETE FROM recipes WHERE id = ? ";
        return jdbcTemplate.update(sql, id);
    }
}
