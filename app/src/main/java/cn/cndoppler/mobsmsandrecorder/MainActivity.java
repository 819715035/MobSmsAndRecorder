package cn.cndoppler.mobsmsandrecorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity {

    private Button getCodeBtn;
    private Button sendCodeBtn;
    private Button startRecorderBth;
    private Button stopRecorderBtn;
    private EditText codeEt;
    private EventHandler eventHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCodeBtn = (Button) findViewById(R.id.sendSms);
        sendCodeBtn = (Button) findViewById(R.id.submit);
        startRecorderBth = (Button) findViewById(R.id.start_record);
        stopRecorderBtn = (Button) findViewById(R.id.stop_record);
        codeEt = (EditText) findViewById(R.id.code_et);
        //获取验证码
        getCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.getVerificationCode("86", "13580701159");
            }
        });
        //提交验证码
        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.submitVerificationCode("86","13580701159",codeEt.getText().toString().trim());
            }
        });

        //开始录屏
        startRecorderBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //停止录屏
        stopRecorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sendSMS();

    }

    private void sendSMS() {
        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        SMSSDK.setAskPermisionOnReadContact(true);

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "得到验证码", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
