package test.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

/**
 *
 * 画像透過処理クラス
 *
 */
public class ImagePermeabilized {

	public static void main(String[] args) {

		System.out.println("**** 処理開始 ****");

		try {
			Properties properties = getProperties();

			// 設定ファイルの対象フォルダから「.peg」ファイルを取得
			final List<File> fileList = (List<File>) FileUtils.listFiles(new File(properties.getProperty("img_dir")), FileFilterUtils.suffixFileFilter(".png"), FileFilterUtils.trueFileFilter());

			for (final File file : fileList) {

				System.out.println(file.getPath());

				// 画像を取得
				BufferedImage readImg = ImageIO.read(file);

				int width = readImg.getWidth(); // 横の幅取得
				int height = readImg.getHeight(); // 縦の高さ取得

				BufferedImage writeImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

				for (int y = 0; y < height; ++y) {
					for (int x = 0; x < width; ++x) {
						if (readImg.getRGB(x, y) == Color.white.getRGB()) {
							// 白を透過
							writeImg.setRGB(x, y, 0);
						} else {
							// 白以外はそのまま
							writeImg.setRGB(x, y, readImg.getRGB(x, y));
						}
					}
				}

				// 画像を書き出し
				ImageIO.write(writeImg, "png", new File(file.getAbsolutePath().replace(".png", "_a.png")));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("**** 処理終了 ****");

	}

	/***
	 * 設定ファイル取得
	 * @return
	 */
	private static Properties getProperties() {
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream("image.properties");
			properties.load(inputStream);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
}
