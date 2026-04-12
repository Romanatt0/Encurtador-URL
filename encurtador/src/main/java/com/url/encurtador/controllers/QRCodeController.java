package com.url.encurtador.controllers;

import com.url.encurtador.services.QRCodeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<byte[]> getQRCode(@PathVariable String codigo, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/dev";
        return qrCodeService.getQRCodeByUrlEncurtada(codigo, baseUrl)
                .map(qrCode -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(qrCode))
                .orElse(ResponseEntity.notFound().build());
    }
}
