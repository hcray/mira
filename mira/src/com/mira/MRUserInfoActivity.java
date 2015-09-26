package com.mira;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;
import org.kymjs.kjframe.KJBitmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.DetectionBean;
import com.bean.DetectionRet;
import com.bean.ResultBean;
import com.bean.RetUserBean;
import com.bll.MRTestBLL;
import com.common.StringUtils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.model.TestModel;
import com.model.User;
import com.umeng.analytics.MobclickAgent;
import com.utils.DateUtil;
import com.utils.FileUtil;
import com.utils.HttpKit;
import com.utils.ImageUtils;
import com.view.wheelView.JudgeDate;
import com.view.wheelView.ScreenInfo;
import com.view.wheelView.WheelMain;

public class MRUserInfoActivity extends Activity {

	private LinearLayout backbtn;

	private EditText et_sign;

	private EditText et_nickName;

	private RadioGroup rg_sex;

	private RadioButton rb_male;

	private RadioButton rb_female;

	private Button btn_save;

	private LinearLayout llBirthday;

	private LinearLayout llSex;

	private LinearLayout llNick;
	
	private TextView tvBirthday;
	
	private TextView tvSex;
	
	private TextView tvNick;

	private WheelMain wheelMain;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private int selectSex;

	private User curUser;
	
	/**
	 * 头像图片
	 */
	private ImageView ivUserface;
	
	private final static int CROP = 200;
	
    private Uri origUri;
    private Uri cropUri;
    private File protraitFile;
    private Bitmap protraitBitmap;
	private String protraitPath;
	
    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/mira/Portrait/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_user_info);

		// et_sign = (EditText)
		// this.findViewById(R.id.userInfo_activity_et_user_sign);
		// et_nickName = (EditText)
		// this.findViewById(R.id.userInfo_activity_et_nick_name);
		// rg_sex = (RadioGroup)
		// this.findViewById(R.id.userInfo_activity_radioGroup_sex);
		// rb_male = (RadioButton)
		// this.findViewById(R.id.userInfo_activity_radioMale);
		// rb_female = (RadioButton)
		// this.findViewById(R.id.userInfo_activity_radioFemale);

		tvBirthday = (TextView) this.findViewById(R.id.userInfo_activity_tv_birthday_value);
		llBirthday = (LinearLayout) this.findViewById(R.id.userInfo_activity_ll_birthday_value);
		Calendar calendar = Calendar.getInstance();
		tvBirthday.setText(calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "");
		llBirthday.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater
						.from(MRUserInfoActivity.this);
				final View timepickerview = inflater.inflate(
						R.layout.timepicker, null);
				ScreenInfo screenInfo = new ScreenInfo(MRUserInfoActivity.this);
				wheelMain = new WheelMain(timepickerview);
				wheelMain.screenheight = screenInfo.getHeight();
				String time = tvBirthday.getText().toString();
				Calendar calendar = Calendar.getInstance();
				if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
					try {
						calendar.setTime(dateFormat.parse(time));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				wheelMain.initDateTimePicker(year, month, day);
				new AlertDialog.Builder(MRUserInfoActivity.this)
						.setTitle("生日")
						.setView(timepickerview)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										String selectTime = wheelMain.getTime();
										if(!selectTime.equalsIgnoreCase(curUser.getBirthday())){
											tvBirthday.setText(selectTime);
											curUser.setBirthday(selectTime);
											AppContext.getInstance().updateUserInfo(curUser);
										}
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}
		});

		backbtn = (LinearLayout) this.findViewById(R.id.userInfoBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRUserInfoActivity.this.finish();
			}
		});

		tvSex = (TextView) this.findViewById(R.id.userInfo_activity_tv_sex_value);
		llSex = (LinearLayout) this.findViewById(R.id.userInfo_activity_ll_sex_value);
		llSex.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MRUserInfoActivity.this);
				builder.setTitle("性别");
				final String[] sex = { "男", "女"};

				int i = 0;
				String curSex = tvSex.getText().toString();
				if ("男".equalsIgnoreCase(curSex)) {
					i = 0;
				} else if ("女".equalsIgnoreCase(curSex)) {
					i = 1;
				} /*else if ("保密".equalsIgnoreCase(curSex)) {
					i = 2;
				}*/
				// 设置一个单项选择下拉框
				/**
				 * 第一个参数指定我们要显示的一组下拉单选框的数据集合 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女'
				 * 会被勾选上 第三个参数给每一个单选项绑定一个监听器
				 */
				builder.setSingleChoiceItems(sex, i,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								selectSex = which;
							}
						});
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if(curUser.getSex() != selectSex + 1){
									tvSex.setText(sex[selectSex]);
									curUser.setSex(selectSex + 1);
									AppContext.getInstance().updateUserInfo(curUser);
								}

							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				builder.show();
			}
		});

		tvNick = (TextView) this.findViewById(R.id.userInfo_activity_tv_nick_name_value);
		llNick = (LinearLayout) this.findViewById(R.id.userInfo_activity_ll_nick_name_value);
		llNick.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MRUserInfoActivity.this);
				builder.setTitle("昵称");
				// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
				View view = LayoutInflater.from(MRUserInfoActivity.this)
						.inflate(R.layout.nick_name, null);
				// 设置我们自己定义的布局文件作为弹出框的Content
				builder.setView(view);

				final EditText username = (EditText) view
						.findViewById(R.id.ev_nick_name);
				username.setText(tvNick.getText());

				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String nickName = username.getText().toString().trim();
								// 将输入的用户名和密码打印出来
								if (nickName != null && !nickName.isEmpty()) {
									if(!nickName.equalsIgnoreCase(curUser.getNickName())){
										tvNick.setText(nickName);
										curUser.setNickName(nickName);
										AppContext.getInstance().updateUserInfo(curUser);
									}
								}
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				builder.show();
			}
		});

		// 保存按钮
		btn_save = (Button) this.findViewById(R.id.userInfo_activity_btn_save);
		btn_save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*
				 * String sign = et_sign.getText().toString(); String nickName =
				 * et_nickName.getText().toString();
				 * if(StringUtils.isEmpty(nickName)){
				 * Toast.makeText(v.getContext(),
				 * getString(R.string.message_nickName_is_null),
				 * Toast.LENGTH_SHORT).show(); return; }
				 * 
				 * User user = new User(); user.setNickName(nickName);
				 * user.setSign(sign);; if(rg_sex.getCheckedRadioButtonId() ==
				 * R.id.userInfo_activity_radioMale){ user.setSex("male");
				 * }else{ user.setSex("female"); }
				 * AppContext.getInstance().saveUserInfo(user);
				 */
				//MRUserInfoActivity.this.finish();
				String UUID = AppContext.getInstance().getAppId();
				User user = AppContext.getInstance().getLoginUser();
				String UserId = user.getUserId();
				
				String endTime = dateFormat.format(new Date());
				for (int i = 1; i < 5; i++) {
					HttpKit.getDetection(UUID, UserId, i, "2015-08-01", endTime, new JsonHttpResponseHandler() {
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							Log.d("", "handler: " + errorResponse);
						}
						
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							Log.d("", "handler: " + response.toString());
							Gson gson = new Gson();
							DetectionRet detectionRet = gson.fromJson(response.toString(), DetectionRet.class);
							if(detectionRet != null){
								List<DetectionBean> detectionList = detectionRet.getDetectionList();
								Log.d("", "detectionList.size(): " + detectionList.size());
								for (DetectionBean detectionBean : detectionList) {
									Log.d("", "detectionBean: " + detectionBean.toString());
									
									long time = DateUtil.getTimes(detectionBean.getCheckinTime());
									int part = detectionBean.getPosition();
									short wenDu = Short.parseShort(detectionBean.getTemperature());
									short shiDu = Short.parseShort(detectionBean.getHumidity());
									short shuiFen = Short.parseShort(detectionBean.getWater()); 
									int score = Integer.parseInt(detectionBean.getScore());
									
									TestModel testModel = new TestModel();
									
									testModel.time = time;
									testModel.wenDu = wenDu;
									testModel.shiDu = shiDu;
									testModel.shuiFen = shuiFen;
									testModel.type = part;
									testModel.score = score;
									
									// 保存数据库
									MRTestBLL.addTestModel(testModel, MRUserInfoActivity.this);
								}
							}
							
						}
					});
				}
			}
		});
		
		//换头像
		ivUserface = (ImageView) this.findViewById(R.id.userInfo_activity_iv_userface);
		ivUserface.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//选择图片换头像
				startImagePick();
			}
		});

		initUI();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 初始化
	 */
	private void initUI() {
		curUser = AppContext.getInstance().getLoginUser();
		if (curUser != null) {
			tvNick.setText(curUser.getNickName());
			tvBirthday.setText(curUser.getBirthday());
			int sex = curUser.getSex();
			if (sex == 1) {
				tvSex.setText("男");
			} else if (sex == 2) {
				tvSex.setText("女");
			} else if (sex == 3) {
				tvSex.setText("保密");
			}
			//加载服务端的头像
	        new KJBitmap().displayWithLoadBitmap(ivUserface, curUser.getFace(), R.drawable.head_pic);
		}
	}
	
	
    /**
     * 选择图片裁剪
     * 
     * @param output
     */
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), 2);
            
        } else {
            intent = new Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), 2);
            
        }
    }
    
    /**
     * 拍照后裁剪
     * 
     * @param data 原始图片
     * @param output 裁剪后图片
     */
    private void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }
    
    // 裁剪头像的绝对路径
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
        	Toast.makeText(this, "无法保存上传的头像，请检查SD卡是否挂载", Toast.LENGTH_SHORT).show(); 
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(this, uri);
        }
        String ext = FileUtil.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        String cropFileName = "mira_crop_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);

        cropUri = Uri.fromFile(protraitFile);
        return this.cropUri;
    }
   
    /**
     * 上传新照片
     */
   private void uploadNewPhoto() {
//       showWaitDialog("正在上传头像...");

       // 获取头像缩略图
       if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
           protraitBitmap = ImageUtils.loadImgThumbnail(protraitPath, 200, 200);
       } else {
    	   Toast.makeText(this, "图像不存在，上传失败", Toast.LENGTH_SHORT).show();
       }
       if (protraitBitmap != null) {
    	   try {
    		String uuid = AppContext.getInstance().getAppId();
    		String userId = AppContext.getInstance().getLoginUser().getUserId();
			HttpKit.uploadHeadPic(protraitFile, uuid, userId, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						Log.d("AppContext", "handler: " + response.toString());
						Gson gson = new Gson();
						ResultBean retBean = gson.fromJson(response.toString(), ResultBean.class);
						//成功
						if(retBean.getResultCode() == 0){
							Toast.makeText(MRUserInfoActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
                            // 显示新头像
							ivUserface.setImageBitmap(protraitBitmap);
							String photoUrl = retBean.getPhotoUrl();
							curUser.setFace(photoUrl);
							AppContext.getInstance().updateUserInfo(curUser);
						}else {
							Toast.makeText(MRUserInfoActivity.this, retBean.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						Toast.makeText(MRUserInfoActivity.this, "更换头像失败", Toast.LENGTH_SHORT).show();
						Log.d("AppContext", "handler: onFailure statusCode " + statusCode);
						Log.d("AppContext", "handler: onFailure headers " + headers.toString());
						Log.d("AppContext", "handler: onFailure errorResponse " + errorResponse.toString());
						super.onFailure(statusCode, headers, throwable, errorResponse);
					}
					
					
				});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	   

//           try {
//               OSChinaApi.updatePortrait(AppContext.getInstance()
//                       .getLoginUid(), protraitFile,
//                       new AsyncHttpResponseHandler() {
//
//                           @Override
//                           public void onSuccess(int arg0, Header[] arg1,
//                                   byte[] arg2) {
//                               Result res = XmlUtils.toBean(ResultBean.class, new ByteArrayInputStream(arg2)) .getResult();
//                               if (res.OK()) {
//                                   Toast.makeText(MRUserInfoActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
//                                   // 显示新头像
//                                   ivUserface.setImageBitmap(protraitBitmap);
//                               } else {
//                                   AppContext.showToast(res.getErrorMessage());
//                               }
//                           }
//
//                           @Override
//                           public void onFailure(int arg0, Header[] arg1,
//                                   byte[] arg2, Throwable arg3) {
//                        	   Toast.makeText(MRUserInfoActivity.this, "更换头像失败", Toast.LENGTH_SHORT).show();
//                           }
//
//                           @Override
//                           public void onFinish() {
//                               hideWaitDialog();
//                           }
//                       });
//           } catch (FileNotFoundException e) {
//               Toast.makeText(this, "图像不存在，上传失败", Toast.LENGTH_SHORT).show();
//           }
       }
   }
    
    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
            final Intent imageReturnIntent) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
//        case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
//            startActionCrop(origUri);// 拍照后裁剪
//            break;
        case 2:
            startActionCrop(imageReturnIntent.getData());// 选图后裁剪
            break;
        case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
            uploadNewPhoto();
            break;
        }
    }
}
