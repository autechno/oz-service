package com.aucloud.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;

@Slf4j
public class QRCodeUtil {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int NCS_S_0540_R90B = 0x88BFE9;
    private static final int DDDDD = 0xDECEBCFF;

    public static String genGR(String website/* ,OutputStream output*/) {
        int width = 300;
        int height = 300;
        String format = "png";
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        //Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bm = null;
        try {
            bm = new MultiFormatWriter().encode(website, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            log.error(e.getMessage(), e);
        }
        BufferedImage image = toImage(bm);
        //ImageIO.write(image, format, output);   //把二维码写到response的输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(image, format, baos);//写入流中
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        byte[] bytes = baos.toByteArray();//转换成字节
        String jpg_base64 =  Base64.getEncoder().encodeToString(bytes).trim();//转换成base64串
        jpg_base64 = jpg_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        jpg_base64 = "data:image/jpg;base64,"+jpg_base64;
        //MatrixToImageWriter.writeToFile(bm,format,file);
        /*try {
            File file = new File("/Users/autech/Downloads/a.png");
            ImageIO.write(image,"png",file);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }*/
        return jpg_base64;
    }

    private static BufferedImage toImage(BitMatrix bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int x = 0;x < width; x++){
            for(int y = 0; y < height; y++ ){
                image.setRGB(x, y, bm.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void main(String[] args) throws IOException, WriterException {
        genGR("扇扇风");

    }
}