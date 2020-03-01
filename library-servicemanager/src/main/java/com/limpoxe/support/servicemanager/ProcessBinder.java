package com.limpoxe.support.servicemanager;

import android.os.Bundle;
import android.os.IBinder;

public class ProcessBinder extends android.os.Binder {

    public static final int FIRST_CODE = IBinder.FIRST_CALL_TRANSACTION + 0;

    private final String DESCRIPTOR;

    public ProcessBinder(String descipter) {
        DESCRIPTOR = descipter;
        this.attachInterface(null, DESCRIPTOR);
    }

    @Override
    public final boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case FIRST_CODE: {
                data.enforceInterface(DESCRIPTOR);
                Bundle param;
                if ((0 != data.readInt())) {
                    param = Bundle.CREATOR.createFromParcel(data);
                } else {
                    param = null;
                }
                Bundle result = MethodRouter.routerToInstance(param);
                reply.writeNoException();
                if ((result != null)) {
                    reply.writeInt(1);
                    result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                } else {
                    reply.writeInt(0);
                }
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

}
