package com.phucnguyen.qrcodeexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {

    EditText edtContent;
    Button btnGenerateQrCode;
    ImageView imgQrCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtContent = findViewById(R.id.edtContent);
        btnGenerateQrCode = findViewById(R.id.btnGenerateQrCode);
        imgQrCode = findViewById(R.id.imgQrCode);

        btnGenerateQrCode.setOnClickListener(view -> {
            String content = edtContent.getText().toString().trim();

            if (content.equals(""))
                Toast.makeText(this, "Please add content first", Toast.LENGTH_SHORT).show();
            else {
                Bitmap QrCodeBitmap = generateQrCode(content);
                imgQrCode.setImageBitmap(QrCodeBitmap);
            }
        });
    }

    private Bitmap generateQrCode(String content) {
        int QrCodeImageWidthInPixel = imgQrCode.getMeasuredWidth();
        int QrCodeImageHeightInPixel = imgQrCode.getMeasuredHeight();

        int width = imgQrCode.getWidth();
        int height = imgQrCode.getHeight();
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    width,
                    height);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (bitMatrix.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}