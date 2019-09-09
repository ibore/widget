package me.ibore.widget.dialog.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.List;

import me.ibore.widget.WheelView;
import me.ibore.widget.dialog.AlertDialog;
import me.ibore.widget.picker.OptionsPickerView;

public class DialogRegion<T> implements IDialogView {

    private List<T> provinces;
    private List<List<T>> cities;
    private List<List<List<T>>> districts;

    @Override
    public View getView(AlertDialog dialog, int cornerRadius) {

        OptionsPickerView<T> pickerView = new OptionsPickerView<>(dialog.getContext());

        pickerView.setLinkageData(provinces, cities, districts);
        return null;
    }

    public static class Region  implements Parcelable, WheelView.IWheelEntity {
        private String code;
        private String name;

        protected Region(Parcel in) {
            code = in.readString();
            name = in.readString();
        }

        public static final Creator<Region> CREATOR = new Creator<Region>() {
            @Override
            public Region createFromParcel(Parcel in) {
                return new Region(in);
            }

            @Override
            public Region[] newArray(int size) {
                return new Region[size];
            }
        };

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(code);
            dest.writeString(name);
        }

        @Override
        public String getWheelText() {
            return name;
        }
       
    }
    
}

