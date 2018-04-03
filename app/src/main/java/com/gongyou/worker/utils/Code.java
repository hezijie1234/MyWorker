package com.gongyou.worker.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Author: zlc
 * Date: 2017/5/23.
 */

public class Code {

    private static final char[] CHARS = {
            '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static Code bmpCode;

    public static Code getInstance() {
        if (bmpCode == null)
            bmpCode = new Code();
        return bmpCode;
    }
    private int[] mColors;
    //default settings
    private static final int DEFAULT_CODE_LENGTH = 4;
    private static final int DEFAULT_FONT_SIZE = 22;
    private static final int DEFAULT_LINE_NUMBER = 2;
    private static final float BASE_PADDING_LEFT = 15, RANGE_PADDING_LEFT = 8, BASE_PADDING_TOP = 18, RANGE_PADDING_TOP = 6;
    private static final int DEFAULT_WIDTH = 120, DEFAULT_HEIGHT = 37;

    //settings decided by the layout xml
    //canvas width and height
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;
    private static final int STRIDE=DensityUtils.dp2px(120);//must be >=WIDTH
    //random word space and pading_top
    private float base_padding_left = DensityUtils.dp2px(BASE_PADDING_LEFT),
            range_padding_left = DensityUtils.dp2px(RANGE_PADDING_LEFT),
            base_padding_top = DensityUtils.dp2px(BASE_PADDING_TOP),
            range_padding_top = DensityUtils.dp2px(RANGE_PADDING_TOP);

    //number of chars, lines; font size
    private int codeLength = DEFAULT_CODE_LENGTH, line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;

    //variables
    private String code;
    private int padding_left, padding_top;
    private Random random = new Random();

    //验证码图片
    public Bitmap createBitmap(String num) {
        padding_left = 0;

        Bitmap bp = Bitmap.createBitmap((int) DensityUtils.dp2px(width),(int) DensityUtils.dp2px(height), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bp);

//        code = createCode(num);
        code = num;

        c.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(DensityUtils.dp2px(font_size));
//        paint.setTextSize(font_size);

        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }

        for (int i = 0; i < line_number; i++) {
            drawLine(c, paint);
        }

        c.save(Canvas.ALL_SAVE_FLAG);//保存
        c.restore();//
        return bp;
    }

    public String getCode() {
        return code;
    }

    //验证码
    private String createCode(String num) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width / 2);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width / 2) + width / 2;
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(2);
        paint.setColor(color);
        canvas.drawLine(DensityUtils.dp2px(startX), DensityUtils.dp2px(startY),
                DensityUtils.dp2px(stopX), DensityUtils.dp2px(stopY), paint);
    }

    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
//        int red = random.nextInt(256) / rate;
//        int green = random.nextInt(256) / rate;
//        int blue = random.nextInt(256) / rate;
        int red = random.nextInt(128) / rate;
        int green = random.nextInt(128) / rate;
        int blue = random.nextInt(128) / rate;
        return Color.rgb(red, green, blue);
    }

    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());  //true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
//      paint.setUnderlineText(true); //true为下划线，false为非下划线
//      paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }

    private void randomPadding() {
        padding_left += base_padding_left + random.nextInt((int) range_padding_left);
        padding_top = (int) (base_padding_top + random.nextInt((int) range_padding_top));
    }
//    private int[] initColors() {
//        int[] colors=new int[STRIDE*height];
//        LogUtil.e(STRIDE*height+"---------+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++------------------------");
//        for (int y = 0; y < (int) DensityUtils.dp2px(height); y++) {//use of x,y is legible then the use of i,j
//            for (int x = 0; x < (int) DensityUtils.dp2px(width); x++) {
//                int r = x * 255 / (width - 1);
//                int g = y * 255 / (height - 1);
//                int b = 255 - Math.min(r, g);
//                int a = Math.max(r, g);
////                colors[y*DensityUtils.dp2px(STRIDE)+x]=(a<<24)|(r<<16)|(g<<8)|(b);//the shift operation generates the color ARGB
//                LogUtil.e(y*STRIDE+x+"-------------");
//                colors[y*STRIDE+x] =(a<<24)|(r<<16)|(g<<8)|(b);
//            }
//        }
//        return colors;
//    }
}