/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.apress.cems.repos.impl;

import com.apress.cems.dao.Storage;
import com.apress.cems.repos.StorageRepo;
import com.apress.cems.repos.util.StorageRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Repository
public class JdbcStorageRepo extends JdbcAbstractRepo<Storage> implements StorageRepo {
    private RowMapper<Storage> rowMapper = new StorageRowMapper();

    @Autowired
    public JdbcStorageRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Storage> findById(Long entityId) {
        String sql = "select id, name, location from storage where id= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, entityId));
    }

    @Override
    public Optional<Storage> findByName(String name) {
        String sql = "select id, name, location from storage where name= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, name));
    }

    @Override
    public Optional<Storage> findByLocation(String location) {
        String sql = "select id, name, location from storage where location= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, location));
    }

    @Override
    public void save(Storage storage) {
        jdbcTemplate.update(
                "insert into storage(id, name, location, modified_at, created_at) values(?,?,?,?,?)",
                storage.getId(), storage.getName(), storage.getLocation(), LocalDate.now(), LocalDate.now()
        );
    }

    @Override
    public void delete(Storage entity) {
        jdbcTemplate.update("delete from storage where id =? ", entity.getId());
    }

    @Override
    public Storage update(Storage entity) {
        return null;
    }

    @Override
    public void deleteById(Long entityId) {
        jdbcTemplate.update("delete from storage where id =? ", entityId);
    }

}
