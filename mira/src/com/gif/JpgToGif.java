package com.gif;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class JpgToGif {
	/**
	 * @param pic 要合成的图片路径数组
	 * @param newPic 合成的GIF图片将存在的路径。
	 */
	public void jpgToGif(String pic[], String newPic) {
		try {
			AnimatedGifEncoder e = new AnimatedGifEncoder();
			e.setRepeat(1);
			e.start(newPic);
			for (int i = 0; i < pic.length; i++) {
				// 设置播放的延迟时间
				e.setDelay(300);
				Bitmap src = BitmapFactory.decodeFile(pic[i]);
				e.addFrame(src); // 添加到帧中
			}
			e.finish();// 刷新任何未决的数据，并关闭输出文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
