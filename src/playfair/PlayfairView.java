package playfair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayfairView {
	private static final int WINDOW_WIDTH = 450;// 像素
	private static final int WINDOW_HEIGHT = 270;// 像素
	private static final int FIELD_WIDTH = 20;// 字节
	private static final int AREA_WIDTH = 40;// 字节
	private static final String doubleSignal = new String("Q");
	private static final String oddSignal = new String("oD");

	private static final String LEGEND = "This window is intended for encrypting and decrypting files.";

	// GUI的窗口
	private JFrame window = new JFrame("Encreption");

	// 图例
	private JTextArea legendArea = new JTextArea(LEGEND, 2, AREA_WIDTH);

	private JLabel key = new JLabel("密钥");
	private JTextField keyValue = new JTextField(FIELD_WIDTH);
	private JButton button1 = new JButton("确定");

	private JLabel cleartext = new JLabel("明文");
	private JTextField cleartextValue = new JTextField(FIELD_WIDTH);
	private JButton button2 = new JButton("加密");
	private JButton buttondec = new JButton("解密");
	private JButton button3 = new JButton("清空");

	private JLabel cryptograph = new JLabel("密文");
	private JTextField cryptographValue = new JTextField(FIELD_WIDTH);
	private JButton button4 = new JButton("退出");

	private JLabel Rectangle1 = new JLabel("原矩阵");
	private JTextArea Area1 = new JTextArea();

	private JLabel Rectangle2 = new JLabel("变化后矩阵");
	private JTextArea Area2 = new JTextArea();

	// 构造方法

	public PlayfairView() {
		// 配置GUI
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(200, 100);

		legendArea.setWrapStyleWord(true);
		legendArea.setEditable(false);
		legendArea.setTabSize(4); // 设置制表符的大小为8个字符,初始值为4个字符
		legendArea.setBackground(window.getBackground());// 文本区背景
		legendArea.setForeground(Color.red);// 字体颜色
		legendArea.setFont(new Font("隶书", Font.PLAIN, 14));

		Area1.setEditable(false);
		Area1.setLineWrap(false);
		String s = new String("A B C D E F G HI/JK L M N O P Q R S T U V W X Y Z ");
		for (int i = 0; i < s.length(); i++) {
			if (i != 0 && i % 10 == 0)
				Area1.append("n");
			Area1.append(s.substring(i, i + 1));
		}
		Area1.setBackground(Color.pink);

		Area2.setEditable(true);
		Area2.setLineWrap(false);
		Area2.setEditable(false);
		Area2.setText("                 ");

		Area2.setBackground(Color.pink);
		button1.setToolTipText("生成密钥加密后的字母矩阵");
		button2.setToolTipText("对明文进行加密，生成密文");
		buttondec.setToolTipText("对密文进行解密，生成明文");
		button3.setToolTipText("清空所有文本框");
		button4.setToolTipText("退出系统");
		button1.addActionListener(bl1);
		button2.addActionListener(bl2);
		buttondec.addActionListener(bdecl);
		button3.addActionListener(bl3);
		button4.addActionListener(bl4);

		Box bl = Box.createVerticalBox();
		Box bc = Box.createVerticalBox();
		Box br = Box.createVerticalBox();
		Box bb = Box.createHorizontalBox();

		bl.add(key);
		bl.add(Box.createVerticalGlue());
		bl.add(cleartext);
		bl.add(Box.createVerticalGlue());
		bl.add(cryptograph);

		bc.add(keyValue);
		bc.add(Box.createVerticalGlue());
		bc.add(cleartextValue);
		bc.add(Box.createVerticalGlue());
		bc.add(cryptographValue);

		br.add(button1);
		br.add(Box.createVerticalGlue());
		br.add(button2);
		br.add(Box.createVerticalGlue());
		br.add(buttondec);
		br.add(Box.createVerticalGlue());
		br.add(button3);

		bb.add(Rectangle1);
		bb.add(Area1);
		bb.add(Rectangle2);
		bb.add(Area2);
		bb.add(button4);

		Container c = window.getContentPane();

		c.add(BorderLayout.NORTH, legendArea);
		c.add(BorderLayout.WEST, bl);
		c.add(BorderLayout.CENTER, bc);
		c.add(BorderLayout.SOUTH, bb);
		c.add(BorderLayout.EAST, br);
		window.setVisible(true);
	}

	String AREA2 = "";
	String arr[][] = new String[5][5];
	String ctext = "";

	public static boolean isAlpha(String s) {
		boolean v = true;
		for (int t = 0; t < s.length(); t++) {
			if (!(s.substring(t, t + 1).compareTo("a") >= 0 && s.substring(t, t + 1).compareTo("z") <= 0
					|| s.substring(t, t + 1).compareTo("A") >= 0 && s.substring(t, t + 1).compareTo("Z") <= 0)) {
				v = false;
				break;
			}
		}
		return v;
	}

	private ActionListener bl1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Area2.setText("");
			String Area1v = new String("ABCDEFGHI/JKLMNOPQRSTUVWXYZ");
			String key1 = keyValue.getText();
			String temp = "";
			for (int i = 0; i < key1.length(); i++) {
				if (!key1.substring(i, i + 1).equals(" "))
					temp += key1.substring(i, i + 1);
			}
			key1 = temp;
			boolean isAlpha = PlayfairView.isAlpha(key1);

			if (isAlpha) {
				key1 = key1.toUpperCase();
				String key2 = "";
				for (int i = 0; i < key1.length(); i++) {
					String key1t = key1.substring(i, i + 1);
					if (!key1t.equals("I") && !key1t.equals("J")) {
						if (key2.indexOf(key1t) < 0) {
							key2 += key1t;
							key2 += " ";
						}
					} else {
						if (key2.indexOf(key1t) < 0) {
							key2 = key2.trim();
							key2 += "I/J";
						}
					}
				}
				String key3 = "";
				for (int i = 0; i < Area1v.length(); i++) {
					if (key2.indexOf(Area1v.substring(i, i + 1)) < 0) {
						if (Area1v.substring(i, i + 1).equals("I")) {
							key3 = key3.trim();
							key3 += "I/J";
							i += 2;
						} else {
							key3 += Area1v.substring(i, i + 1);
							key3 += " ";
						}
					}
				}
				AREA2 = key2 + key3;
				for (int i = 0; i < AREA2.length(); i++) {
					if (i != 0 && i % 10 == 0)
						Area2.append("n");
					Area2.append(AREA2.substring(i, i + 1));
				}
			} else
				JOptionPane.showMessageDialog(null, "密钥只能输入A~Z或者a~z之间的字母!", "输入错误", JOptionPane.ERROR_MESSAGE);
			keyValue.setText(key1);
			String area2v = AREA2;
			String area2vt = "";
			for (int i = 0; i < area2v.length(); i++) {
				if (!area2v.substring(i, i + 1).equals(" "))
					area2vt += area2v.substring(i, i + 1);
			}

			int t = 0;
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (!area2vt.substring(t, t + 1).equals("I")) {
						arr[i][j] = area2vt.substring(t, t + 1);
						t++;
					} else {
						arr[i][j] = area2vt.substring(t, t + 1);
						t += 3;
					}
				}
			}
		}
	};

	private ActionListener bl2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String clearv = cleartextValue.getText().trim();
			if (clearv.equals("")) {
				JOptionPane.showMessageDialog(null, "请输入明文，以加密!n(明文只能是a~z或者A~Z之间的字母!)", "加密提示",
						JOptionPane.ERROR_MESSAGE);
			} else {
				String temp = "";
				for (int i = 0; i < clearv.length(); i++) {
					if (!clearv.substring(i, i + 1).equals(" "))
						temp += clearv.substring(i, i + 1);
				}
				clearv = temp;
				boolean isAlpha = PlayfairView.isAlpha(clearv);
				if (isAlpha) {
					if (clearv.equals("qq") || clearv.equals("QQ"))
						JOptionPane.showMessageDialog(null, "不合法字符串，请输入新字符串", "加密提示", JOptionPane.ERROR_MESSAGE);
					else {
						clearv = clearv.toUpperCase();
						ctext = clearv;

						String clearvt = "";
						String cryv = "";
						for (int i = 0; i < clearv.length(); i++) {
							if (!clearv.substring(i, i + 1).equals(" ")) {
								if (clearv.substring(i, i + 1).equals("J"))
									clearvt += "I";
								else
									clearvt += clearv.substring(i, i + 1);
							}
						}
						boolean isOdd = false;
						if (clearvt.length() % 2 != 0)
							isOdd = true;
						if (!isOdd) {
							for (int m = 0; m < clearvt.length(); m += 2) {
								String a = clearvt.substring(m, m + 1);
								String b = clearvt.substring(m + 1, m + 2);
								int[] ar = new int[2];
								int[] br = new int[2];
								for (int i = 0; i < 5; i++) {
									for (int j = 0; j < 5; j++) {
										if (arr[i][j].equals(a)) {
											ar[0] = i;
											ar[1] = j;
										}
										if (arr[i][j].equals(b)) {
											br[0] = i;
											br[1] = j;
										}
									}
								}
								if (ar[0] == br[0] && !a.equals(b)) {
									cryv += arr[ar[0]][(ar[1] + 1) % 5];
									cryv += arr[br[0]][(br[1] + 1) % 5];
								}
								if (ar[1] == br[1] && !a.equals(b)) {
									cryv += arr[(ar[0] + 1) % 5][ar[1]];
									cryv += arr[(br[0] + 1) % 5][br[1]];
								}
								if (ar[0] != br[0] && ar[1] != br[1]) {
									cryv += arr[ar[0]][br[1]];
									cryv += arr[br[0]][ar[1]];
								}
								if (a.equals(b))
									cryv += a + doubleSignal + b;
							}
						} else {
							if (clearvt.length() > 1) {
								for (int m = 0; m < clearvt.length() - 1; m += 2) {
									String a = clearvt.substring(m, m + 1);
									String b = clearvt.substring(m + 1, m + 2);
									int[] ar = new int[2];
									int[] br = new int[2];
									for (int i = 0; i < 5; i++) {
										for (int j = 0; j < 5; j++) {
											if (arr[i][j].equals(a)) {
												ar[0] = i;
												ar[1] = j;
											}
											if (arr[i][j].equals(b)) {
												br[0] = i;
												br[1] = j;
											}
										}
									}
									if (ar[0] == br[0] && !a.equals(b)) {
										cryv += arr[ar[0]][(ar[1] + 1) % 5];
										cryv += arr[br[0]][(br[1] + 1) % 5];
									}
									if (ar[1] == br[1] && !a.equals(b)) {
										cryv += arr[(ar[0] + 1) % 5][ar[1]];
										cryv += arr[(br[0] + 1) % 5][br[1]];
									}
									if (ar[0] != br[0] && ar[1] != br[1]) {
										cryv += arr[ar[0]][br[1]];
										cryv += arr[br[0]][ar[1]];
									}
									if (a.equals(b))
										cryv += a + doubleSignal + b;
								}
								cryv += clearvt.substring(clearvt.length() - 1, clearvt.length()) + oddSignal;
							} else {
								cryv += clearvt + oddSignal;
							}

						}
						cleartextValue.setText(clearv.trim());
						cryptographValue.setText(cryv.trim());
					}

				} else
					JOptionPane.showMessageDialog(null, "明文只能输入A~Z或者a~z之间的字母!", "输入错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	};

	private ActionListener bdecl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String cryv = cryptographValue.getText();
			String crylast = "";
			String ctv = "";
			String aQStore = "";
			String bQStore = "";
			String ctvt = "";
			if (cryv.equals("")) {
				JOptionPane.showMessageDialog(null, "请输入密文，以解密!n(密文只能是a~z或者A~Z之间的字母!)", "解密提示",
						JOptionPane.ERROR_MESSAGE);
			} else {
				String temp = "";
				for (int i = 0; i < cryv.length(); i++) {
					if (!cryv.substring(i, i + 1).equals(" "))
						temp += cryv.substring(i, i + 1);
				}
				cryv = temp;
				boolean isAlpha = PlayfairView.isAlpha(cryv);
				if (isAlpha) {
					boolean isOdd = false;
					if (cryv.substring(cryv.length() - 2, cryv.length()).equals(oddSignal))
						isOdd = true;
					if (isOdd) {
						cryv = cryv.toUpperCase();
						crylast = cryv.substring(cryv.length() - 3, cryv.length() - 2);
						cryv = cryv.substring(0, cryv.length() - 3);
						cryv += " ";
						for (int m = 0; m < cryv.length(); m += 2) {
							if (cryv.substring(m, cryv.length()).length() >= 2) {
								String a = cryv.substring(m, m + 1);
								String b = cryv.substring(m + 1, m + 2);
								if (bQStore.equals(doubleSignal) && a.equals(aQStore)) {
									ctv += aQStore + a;
									m--;
								} else {
									if (bQStore.equals(doubleSignal) && !a.equals(aQStore))
										ctv += ctvt;
									ctvt = "";
									int[] ar = new int[2];
									int[] br = new int[2];
									for (int i = 0; i < 5; i++) {
										for (int j = 0; j < 5; j++) {
											if (arr[i][j].equals(a)) {
												ar[0] = i;
												ar[1] = j;
											}
											if (arr[i][j].equals(b)) {
												br[0] = i;
												br[1] = j;
											}
										}
									}
									if (ar[0] == br[0] && !a.equals(b)) {
										ctvt += arr[ar[0]][(ar[1] + 4) % 5];
										ctvt += arr[br[0]][(br[1] + 4) % 5];
									}
									if (ar[1] == br[1] && !a.equals(b)) {
										ctvt += arr[(ar[0] + 4) % 5][ar[1]];
										ctvt += arr[(br[0] + 4) % 5][br[1]];
									}
									if (ar[0] != br[0] && ar[1] != br[1]) {
										ctvt += arr[ar[0]][br[1]];
										ctvt += arr[br[0]][ar[1]];
									}
									if (!b.equals(doubleSignal))
										ctv += ctvt;
									aQStore = a;
									bQStore = b;
								}
							} else
								break;
						}
						ctv += crylast;
						cryptographValue.setText(cryv.trim() + crylast + oddSignal);
					} else {
						cryv = cryv.toUpperCase();
						cryv += " ";
						for (int m = 0; m < cryv.length(); m += 2) {
							if (cryv.substring(m, cryv.length()).length() >= 2) {
								String a = cryv.substring(m, m + 1);
								String b = cryv.substring(m + 1, m + 2);
								if (bQStore.equals(doubleSignal) && a.equals(aQStore)) {
									ctv += aQStore + a;
									m--;
								} else {
									if (bQStore.equals(doubleSignal) && !a.equals(aQStore))
										ctv += ctvt;
									ctvt = "";
									int[] ar = new int[2];
									int[] br = new int[2];
									for (int i = 0; i < 5; i++) {
										for (int j = 0; j < 5; j++) {
											if (arr[i][j].equals(a)) {
												ar[0] = i;
												ar[1] = j;
											}
											if (arr[i][j].equals(b)) {
												br[0] = i;
												br[1] = j;
											}
										}
									}
									if (ar[0] == br[0] && !a.equals(b)) {
										ctvt += arr[ar[0]][(ar[1] + 4) % 5];
										ctvt += arr[br[0]][(br[1] + 4) % 5];
									}
									if (ar[1] == br[1] && !a.equals(b)) {
										ctvt += arr[(ar[0] + 4) % 5][ar[1]];
										ctvt += arr[(br[0] + 4) % 5][br[1]];
									}
									if (ar[0] != br[0] && ar[1] != br[1]) {
										ctvt += arr[ar[0]][br[1]];
										ctvt += arr[br[0]][ar[1]];
									}
									if (!b.equals(doubleSignal))
										ctv += ctvt;
									aQStore = a;
									bQStore = b;
								}
							} else
								break;
						}
						cryptographValue.setText(cryv.trim());
					}

				} else
					JOptionPane.showMessageDialog(null, "密文只能输入A~Z或者a~z之间的字母!", "输入错误", JOptionPane.ERROR_MESSAGE);
			}
			cleartextValue.setText(ctv.trim());
		}
	};

	private ActionListener bl3 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			keyValue.setText("");
			cleartextValue.setText("");
			cryptographValue.setText("");
			Area2.setText("                 ");
		}
	};

	private ActionListener bl4 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int v = JOptionPane.showConfirmDialog(null, "确定要退出本系统？", "choose yes", JOptionPane.YES_NO_OPTION);
			if (v == 0) {
				JOptionPane.showMessageDialog(null, "感谢使用本系统，您将退出此程序n制作人：悠优Marian2009年", "退出系统",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
	};

	// main():应用程序入口
	public static void main(String args[]) {
		new PlayfairView();
	}
}
