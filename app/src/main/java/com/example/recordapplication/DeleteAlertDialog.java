package com.example.recordapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.viewmodel.CreationExtras;

public class DeleteAlertDialog extends AppCompatDialogFragment {

    private deleteAlertListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_alert_dialog, null);
        builder.setView(view)
                .setTitle("Add Flavour")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean success = false;
                        listener.returnSuccess(success);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean success = true;
                        listener.returnSuccess(success);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteAlertDialog.deleteAlertListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement DeleteAlertDialog listener");
        }
    }

    public interface deleteAlertListener {
        void returnSuccess(boolean success);
    }
}
