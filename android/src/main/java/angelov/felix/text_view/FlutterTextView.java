package angelov.felix.text_view;
//
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import static io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

/////
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
////////////////
public class FlutterTextView implements PlatformView, MethodCallHandler  {
    //private final TextView textView;
    private final WebView webView;
    private final MethodChannel methodChannel;
    private String sFileName,sUrl,sUserAgent;
    private Activity activity;
    private Context context;

    FlutterTextView(Context context, BinaryMessenger messenger, int id) {
        this.context = context;
        //textView = new TextView(context);
        webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://ovh.net/files/1Mio.dat");
        methodChannel = new MethodChannel(messenger, "plugins.felix.angelov/textview_" + id);
        methodChannel.setMethodCallHandler(this);



    }
    //override abstract method
    @Override
    public View getView() {
       return webView;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method) {
            case "setText":
                setText(methodCall, result);
                break;
            default:
                result.notImplemented();
        }

    }

    private void setText(MethodCall methodCall, Result result) {
        String urltext = (String) methodCall.arguments;
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urltext);
        //final Context c = this;
        webView.setDownloadListener(new DownloadListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                /*String filename = URLUtil.guessFileName(url,contentDisposition,getFileType(url));*/
                sFileName = "filename";
                sUrl = "url";
                sUserAgent = "userAgent";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //if (== PackageManager.PERMISSION_GRANTED) {
                    System.out.println("userAgent");
                    System.out.println(userAgent);
                    downloadFile(sFileName,url,userAgent);
                    //  } else {
                    //requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
                    //   }
                } else {
                    downloadFile(sFileName,url,userAgent);
                }
            }
        });
        //textView.setText(text);
        //webView.setText(text);
        result.success(null);

    }


    ///
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void downloadFile(String filename, String url, String userAgent) {
        try {
            String cookie = CookieManager.getInstance().getCookie(url);
            System.out.println("cookie");
            System.out.println(cookie);

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111 Downloads");
            //"txtReSzType=normal; tree1Selected=; tree1State="
            //"Mozilla/5.0 (Linux; Android 10; Redmi Note 7 Pro Build/QKQ1.190915.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/89.0.4389.86 Mobile Safari/537.36"
            request.setTitle("RDInstallmentReport05-05-2021.pdf")
                    .setDescription("Downloading...")
                    .addRequestHeader("cookie",cookie)
                    .addRequestHeader("User-Agent",userAgent)

                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE|
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            System.out.println("start downloads");
            downloadManager.enqueue(request);
            System.out.println("Started Downloads");
            // Toast.makeText(this, "Download Started", Toast.LENGTH_SHORT).show();

            sUrl = "";
            sFileName = "";
            sUserAgent = "";
        }catch (Exception ignored){
            System.out.println("failed log Downloads");
            System.out.println(ignored.toString());
            //Toast.makeText(this, ignored.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    //ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    //final Context c = this;



    /*private String getFileType(String url){
        ContentResolver contentResolver = activity.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(Uri.parse(url)));
    }*/

   /* @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1001){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if (!sUrl.equals("")&&!sFileName.equals("")&&!sUserAgent.equals("")){
                    downloadFile(sFileName,sUrl,sUserAgent);
                }
            }
        }
    }*/
    @Override
    public void dispose() {}
}