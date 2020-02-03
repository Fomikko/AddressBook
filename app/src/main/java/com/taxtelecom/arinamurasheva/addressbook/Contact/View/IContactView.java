package com.taxtelecom.arinamurasheva.addressbook.Contact.View;

import android.graphics.Bitmap;
import android.view.View;

public interface IContactView {
    void onEmailClicked(View view);
    void onPhoneClicked(View view);
    void requestContactPhoto(String contactId);
    void setContactPhoto(Bitmap contactPhoto);
}
