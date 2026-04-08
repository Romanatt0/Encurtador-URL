package com.url.encurtador.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.url.encurtador.models.UrlModel;
import com.url.encurtador.repositories.UrlRepository;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Service
public class QRCodeService {

    private final UrlRepository urlRepository;

    public QRCodeService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public byte[] generateQRCodeImage(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        return baos.toByteArray();
    }

    public Optional<byte[]> getQRCodeByUrlEncurtada(String codigo) {
        Optional<UrlModel> urlModel = urlRepository.findByUrlEncurtada(codigo);
        if (urlModel.isEmpty()) {
            return Optional.empty();
        }
        try {
            byte[] qrCode = generateQRCodeImage(urlModel.get().getUrl(), 300, 300);
            return Optional.of(qrCode);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
