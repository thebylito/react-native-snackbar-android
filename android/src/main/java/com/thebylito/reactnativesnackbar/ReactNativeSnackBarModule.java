//  Created by react-native-create-bridge

package com.thebylito.reactnativesnackbar;

import android.graphics.Color;
import androidx.annotation.Nullable;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;
import android.view.ViewGroup;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactNativeSnackBarModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "ReactNativeSnackBar";
    private static ReactApplicationContext reactContext = null;
    private List<Snackbar> mActiveSnackbars = new ArrayList<>();

    public ReactNativeSnackBarModule(ReactApplicationContext context) {
        // Pass in the context to the constructor and save it so you can emit events
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        super(context);

        reactContext = context;
    }

    @Override
    public String getName() {
        // Tell React the name of the module
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        return REACT_CLASS;
    }

    @Override
    public Map<String, Object> getConstants() {
        // Export any constants to be used in your native module
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        final Map<String, Object> constants = new HashMap<>();
        constants.put("LENGTH_LONG", Snackbar.LENGTH_LONG);
        constants.put("LENGTH_SHORT", Snackbar.LENGTH_SHORT);
        constants.put("LENGTH_INDEFINITE", Snackbar.LENGTH_INDEFINITE);
        return constants;
    }

    @ReactMethod
    public void showSnack(ReadableMap options, final Callback callback) {
        ViewGroup view;
        String message = options.hasKey("message") ? options.getString("message") : "";
        int duration = options.hasKey("duration") ? options.getInt("duration") : Snackbar.LENGTH_SHORT;
        try {
            view = getCurrentActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        final Snackbar mySnack = Snackbar.make(view, message, duration);
        if (options.hasKey("backgroundColor")) {
            String backgroundColor = options.getString("backgroundColor");
            Integer snackBarBackgroundColor;
            try {
                snackBarBackgroundColor  =  Color.parseColor(String.valueOf(backgroundColor));
            } catch (Exception e) {
                snackBarBackgroundColor = Color.GRAY;
            }
            View snackBarView = mySnack.getView();
            snackBarView.setBackgroundColor(snackBarBackgroundColor);
        }

        final Snackbar.Callback mCallBack = new Snackbar.Callback() {
            @Override
            public void onShown(Snackbar snackbar) {
                emitDeviceEvent("onShow", null);
            }

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                emitDeviceEvent("onDismiss", null);
            }
        };

        mySnack.addCallback(mCallBack);
        if (options.hasKey("action")) {
            ReadableMap actionDetails = options.getMap("action");
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.invoke();
                }
            };
            if (actionDetails.hasKey("txtColor")) {
                String txtColor = actionDetails.getString("txtColor");
                Integer btnTxtCollor;
                try {
                    btnTxtCollor  =  Color.parseColor(String.valueOf(txtColor));
                } catch (Exception e) {
                    btnTxtCollor = Color.WHITE;
                }
                mySnack.setActionTextColor(btnTxtCollor);
            }
            mySnack.setAction(actionDetails.getString("title"), onClickListener);
        }
        mySnack.show();
    }

    private void emitDeviceEvent(String eventName, @Nullable WritableMap eventData) {
        // A method for emitting from the native side to JS
        // https://facebook.github.io/react-native/docs/native-modules-android.html#sending-events-to-javascript
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, eventData);
    }
}
