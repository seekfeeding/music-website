package com.example.yin.service.impl;

import com.example.yin.common.R;
import com.example.yin.mapper.SingerMapper;
import com.example.yin.model.domain.Singer;
import com.example.yin.model.request.SingerRequest;
import com.example.yin.service.SingerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public boolean updateSingerMsg(Singer singer) {
        return singerMapper.updateSingerMsg(singer) > 0;
    }

    @Override
    public R updateSingerPic(MultipartFile avatorFile, int id) {
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "singerPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/singerPic/" + fileName;
        try {
            avatorFile.transferTo(dest);
        } catch (IOException e) {
            return R.fatal("上传失败" + e.getMessage());
        }
        Singer singer = new Singer();
        singer.setId(id);
        singer.setPic(imgPath);
        if (singerMapper.updateSingerPic(singer) > 0) {
            return R.success("上传成功", imgPath);
        } else {
            return R.error("上传失败");
        }
    }

    @Override
    public R deleteSinger(Integer id) {
        if (singerMapper.deleteSinger(id) > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    @Override
    public R allSinger() {
        return R.success(null, singerMapper.allSinger());
    }

    @Override
    public R addSinger(SingerRequest addSingerRequest) {
        Singer singer = new Singer();
        BeanUtils.copyProperties(addSingerRequest, singer);
        String pic = "/img/avatorImages/user.jpg";
        singer.setPic(pic);
        if (singerMapper.insertSelective(singer) > 0) {
            return R.success("添加成功");
        } else {
            return R.error("添加失败");
        }
    }

    @Override
    public R singerOfName(String name) {
        return R.success(null, singerMapper.singerOfName(name));
    }

    @Override
    public R singerOfSex(Integer sex) {
        return R.success(null, singerMapper.singerOfSex(sex));
    }
}
