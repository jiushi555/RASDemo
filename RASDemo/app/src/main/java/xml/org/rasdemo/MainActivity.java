package xml.org.rasdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MainActivity extends Activity {
    private Button mBtn;
    private TextView mTv;
    private EditText mEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.button);
        mEdt = (EditText) findViewById(R.id.ming_edt);
        mTv = (TextView) findViewById(R.id.result_tv);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String data = mEdt.getText().toString().trim();
                    encryptData(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void encryptData(String data) throws Exception {
        HashMap<String, Object> map = null;
        String ming = data;
        String ming_, mi;

        map = RSAUtils.getKeys();

        //生成公钥和私钥
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");

        //模
        String modulus = publicKey.getModulus().toString();
        //公钥指数
        String public_exponent = publicKey.getPublicExponent().toString();
        //私钥指数
        String private_exponent = privateKey.getPrivateExponent().toString();
        //使用模和指数生成公钥和私钥
        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);
        //加密后的密文
        mi = RSAUtils.encryptByPublicKey(ming, pubKey);
        //解密后的明文
        ming_ = RSAUtils.decryptByPrivateKey(mi, priKey);

        mTv.setText("加密后：\n"+mi+"\n"+"解密后：\n"+ming_);
    }

}
