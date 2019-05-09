package com.example.musicmanagment.controller;


import com.example.musicmanagment.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.websocket.server.PathParam;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api")
public class MusicManagementController {

    private JdbcTemplate jdbcTemplate;

    private String dataTableName;



    @Autowired
    public MusicManagementController(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path="/new")
    @Transactional
    public @ResponseBody
    int createSong(@RequestParam String name, @RequestParam String singer,
                      @RequestParam  boolean favor, @RequestParam boolean listened) {
        System.out.println("add request arrived and name is  "+ name+ "singer is "+ singer+ "favor is "+ favor + "listen is  "+ listened);

        int row= 0, id = 10;
        try {
            //String sql = "insert into music (`name`,`singer`,`favor`,`listened`)" + "VALUES (?, ?, ?,?)";
            String sql2 = "insert into music (name,singer,favor,listened) values ('"+name+
                    "','"+ singer+"',"+ favor+","+ listened+")";
            System.out.println("sql2=   " + sql2);
           // row = jdbcTemplate.update(sql, name, singer,favor, listened);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            PreparedStatementCreator preparedStatementCreator = con -> {
                PreparedStatement ps = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                return ps;
            };

            jdbcTemplate.update(preparedStatementCreator, keyHolder);

            id = keyHolder.getKey().intValue();

            System.out.println("看下能否拿到最终的自增的id-------------->" + id);




        } catch (Exception e) {
            System.out.println(e.getMessage());

        }




        System.out.println("Logon request arriving... and id is" + id);
        return id;

    }




    @CrossOrigin
    @GetMapping(path="allSongs")
    public @ResponseBody Iterable<Song> getAllSongs() {
        String sql = "select * from music";
        List<Song> allSongs = new ArrayList<Song>();

        try {

            List rows = jdbcTemplate.queryForList(sql);
            for (Object objsong : rows) {
                Map rowSong = (Map) objsong;
                Song song = new Song();

                song.setId((int) rowSong.get("id"));
                song.setName((String) rowSong.get("name"));
                song.setSinger((String) rowSong.get("singer"));
                song.setFavor((boolean) rowSong.get("favor"));
                song.setListened((boolean) rowSong.get("listened"));


                allSongs.add(song);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return allSongs;
    }




    @CrossOrigin("http://localhost:4200")
    @RequestMapping(method = RequestMethod.PUT, path="/update/{id}")
    @Transactional
    public @ResponseBody int updateSong(@RequestParam String name,
                                        @RequestParam String singer,
                                        @RequestParam boolean favor,
                                        @RequestParam boolean listened,
                                        @PathVariable int id ){
//    public @ResponseBody int updateSong(@RequestParam Song song,
////                                        @RequestParam String singer,
////                                        @RequestParam boolean favor,
////                                        @RequestParam boolean listened,
//                                        @PathVariable int id ){

        String sql = "update music set name=?, singer=?, favor=?,listened=? where id =?;";
        int row = 0;
        try {
            row = this.jdbcTemplate.update(sql,name,singer,favor,listened, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return row;

    }






    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, path="/songs/{id}")
    @Transactional
    public @ResponseBody int deleteSong(@PathVariable int id) {
        String sql = "delete from music where id=?";
        int rows = 0;

        try {
            rows = this.jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rows;
    }
}
