package org.envirocar.app.views.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.google.android.material.snackbar.Snackbar;

import org.envirocar.app.R;
import org.envirocar.core.entity.User;
import org.envirocar.core.utils.TextViewUtils;

public class SnackbarUtil {

    public static void showSnackbarWithAction(View view, String message, String actionMessage, View.OnClickListener listener) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionMessage, listener).show();
    }

    public static void showGrantMicrophonePermission(View view, Context context, Activity activity) {
        showSnackbarWithAction(view, context.getString(R.string.microphone_permission_denied),
                context.getString(R.string.grant_permission),
                v -> activity.startActivity(
                        new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + context.getPackageName()
                                )
                        )
                )
        );
    }

    public static void showVoiceTriggeredSnackbar(View view, Activity activity, Context context, View anchorView, User user) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        View customSnackView = activity.getLayoutInflater().inflate(R.layout.voice_trigger_snack_bar_layout, null);

        // set the background of the default snackbar as transparent
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

        // now change the layout of the snackbar
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        // set padding of the all corners as 0
        snackbarLayout.setPadding(0, 0, 0, 0);

        TextView bottomsheetFooter = customSnackView.findViewById(R.id.bottomsheet_footer);
        TextView greetHeading = customSnackView.findViewById(R.id.bottomsheet_greet_heading);

        Resources resource = activity.getResources();
        String footerText =
                String.format(
                        resource.getString(R.string.voice_trigger_bottomsheeet_footer),
                        new TextViewUtils().getColoredSpanned(resource.getString(R.string.envirocar),
                                ContextCompat.getColor(context, R.color.cario_color_primary))
                );

        bottomsheetFooter.setText(HtmlCompat.fromHtml(footerText, HtmlCompat.FROM_HTML_MODE_LEGACY));

        if (user != null) {
            greetHeading.setText(
                    String.format(
                            resource.getString(R.string.voice_trigger_bottomsheeet_greet_heading),
                            user.getUsername() + "!")
            );
        } else {
            greetHeading.setText(
                    String.format(
                            resource.getString(R.string.voice_trigger_bottomsheeet_greet_heading),
                            "")
            );
        }

        // add the custom layout
        snackbarLayout.addView(customSnackView, 0);

        // set the anchor view if provided
        if (anchorView != null) {
            snackbar.setAnchorView(anchorView);
        }
        snackbar.show();
    }
}
