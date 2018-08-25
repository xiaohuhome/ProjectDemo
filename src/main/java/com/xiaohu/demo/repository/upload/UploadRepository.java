package com.xiaohu.demo.repository.upload;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiaohu.demo.entity.upload.UploadAttachment;

public interface UploadRepository extends JpaRepository<UploadAttachment,String>{
	
}
