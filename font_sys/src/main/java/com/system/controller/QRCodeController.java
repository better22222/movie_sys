package com.system.controller;

import com.system.common.R;
import com.system.service.impl.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
public class QRCodeController {

    @Autowired
    QRCodeService qrCodeService;

    @PostMapping(value = "/add")
    public R add(String content){
        //购票相关信息 content
        try {
            return R.ok().data("QRCode",qrCodeService.crateQRCode(content,200,200));
        } catch (IOException e) {
            e.printStackTrace();
            return R.error(50005,"二维码失败");
        }
    }

}
