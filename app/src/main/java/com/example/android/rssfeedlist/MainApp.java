package com.example.android.rssfeedlist;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import com.parse.Parse;


@ReportsCrashes(
        formUri = "https://medo.cloudant.com/acra-example/_design/acra-storage/_update/report",
        reportType = HttpSender.Type.JSON,
        httpMethod = HttpSender.Method.POST,
        formUriBasicAuthLogin = "tubtakedstinumenterences",
        formUriBasicAuthPassword = "igqMFFMatvtMXVCKgy7u6a5W",
        formKey = "", // This is required for backward compatibility but not used
        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PACKAGE_NAME,
                ReportField.REPORT_ID,
                ReportField.BUILD,
                ReportField.STACK_TRACE
        },
        mode = ReportingInteractionMode.TOAST
       //resToastText = R.string.toast_crash
)

public class MainApp extends Application {

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate() {
        Parse.initialize(this, "gvCXkYCAvqVqWXAm0umeroUuYBvDZpGlBq1yScDr","0ymsBBALl9nfSWUg8wvwigxBqrsRvqYRphmLlZet"); 
        ACRA.init(this);  
       // ACRA.getErrorReporter().setReportSender(new LocalSender(this));  
        super.onCreate();
        
    }
}
