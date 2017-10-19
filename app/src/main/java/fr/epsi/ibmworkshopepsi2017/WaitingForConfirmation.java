package fr.epsi.ibmworkshopepsi2017;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import fr.epsi.ibmworkshopepsi2017.Models.Delivery;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaitingForConfirmation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaitingForConfirmation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingForConfirmation extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "isFromCompanyToCompany";

    // TODO: Rename and change types of parameters
    private boolean isFromCompanyToCompany;
    private Delivery deliveryClicked;

    private OnFragmentInteractionListener mListener;

    public WaitingForConfirmation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WaitingForConfirmation.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingForConfirmation newInstance(Boolean isFromCompanyToCompany, Delivery delivery) {
        WaitingForConfirmation fragment = new WaitingForConfirmation();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isFromCompanyToCompany);
        args.putSerializable("currentDelivery", delivery);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isFromCompanyToCompany = getArguments().getBoolean(ARG_PARAM1);
            deliveryClicked = (Delivery) getArguments().getSerializable("currentDelivery");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_waiting_for_confirmation, container, false);




        TextView textView = (TextView) v.findViewById(R.id.rfidLayout);
        TextView address = (TextView) v.findViewById(R.id.addressLivraison);
        TextView clientId = (TextView) v.findViewById(R.id.ClientId);
        TextView price = (TextView) v.findViewById(R.id.Price);
        TextView quantity = (TextView) v.findViewById(R.id.Quantity);
        TextView type = (TextView) v.findViewById(R.id.TypeDelivery);
        ImageView qrImage = (ImageView)v.findViewById(R.id.QRcode);
        address.setText(deliveryClicked.getAdress());
        clientId.setText(String.valueOf(deliveryClicked.getClientID()));

        price.setText(String.valueOf(deliveryClicked.getPrice()));
        quantity.setText(String.valueOf(deliveryClicked.getQuantity()));
        if(deliveryClicked.getTypeDelivery() == 0){
            type.setText("B2B");
        }
        else{
            type.setText("B2C");
        }

        if(isFromCompanyToCompany){

            textView.setText("En attente du QR Code");

        }
        textView.setVisibility(View.VISIBLE);


        String finalData = "deliveryID : " + deliveryClicked.getDeliveryID() + " " + "packageID : " + deliveryClicked.getPackageId();
        //QRCODE
        QRGEncoder qrgEncoder = new QRGEncoder(finalData, null, QRGContents.Type.TEXT, 16);
        try {
            // Getting QR-Code as Bitmap
           Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v(TAG, e.toString());
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onBackButtonPressed (Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
