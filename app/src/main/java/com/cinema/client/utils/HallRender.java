package com.cinema.client.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cinema.client.R;
import com.cinema.client.etc.SitButton;
import com.cinema.client.requests.entities.HallAPI;
import com.cinema.client.requests.entities.HallCellAPI;
import com.cinema.client.requests.entities.HallCellCustomAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HallRender {


    Context context;
    String sector;


    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;


    private List<String> selectedPlaces;

    private List<HallCellAPI> selectedAbsoluteCells;

//    private List<HallCellAPI> selectedRelativeCells;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public HallRender(Context context) {
        this.context = context;
//        sharedpreferences = context.getSharedPreferences("places", Context.MODE_PRIVATE);
//        sharedpreferences= context.getSharedPreferences("selectedPlaces",Context.MODE_PRIVATE);
//        editor = sharedpreferences.edit();

        selectedPlaces = new ArrayList<>();
        selectedAbsoluteCells = new ArrayList<>();
//        selectedRelativeCells = new ArrayList<>();

    }

//    public HallRender(Context context, ArrayList<String> selectedPlaces) {
//        this.context = context;
//        pref = context.getSharedPreferences("selectedPlaces", 0);
//        editor = pref.edit();
//
//        this.selectedPlaces = selectedPlaces;
//
//    }

    enum Status {
        DISABLED,
        FREE,
        BOOKED,
        BOUGHT
    }


    public TableLayout render(HallAPI hall, TableLayout tableLayout) {
        //
        initSelectedPlaces();
        //
        int row = hall.getRow();
        int col = hall.getCol();
        sector = hall.getSector();

        for (int i = 1; i <= row; i++) {
            TableRow tr = new TableRow(context);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.setBaselineAligned(false);

            TextView temp1 = new TextView(context);
            temp1.setText(i + "");
            tr.addView(temp1);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

            final float scale1 = context.getResources().getDisplayMetrics().density;
            int margin = (int) (10 * scale1 + 0.5f);

            params.setMargins(margin, 0, margin, 0);
            temp1.setLayoutParams(params);


            for (int j = 1; j <= col; j++) {
//                Button b = new Button(context);
                SitButton b = new SitButton(context);
//                b.setText(j +1+ "");
                final float scale = context.getResources().getDisplayMetrics().density;
//                int pixels = (int) (50 * scale + 0.5f);
                int pixels = (int) (60 * scale + 0.5f); // Good
//                int pixels = (int) (70 * scale + 0.5f);

                b.setLayoutParams(new TableRow.LayoutParams(pixels, pixels));
                b.setText((i) + "-" + (j));

                HallCellAPI absoluteLocation = new HallCellAPI();
                absoluteLocation.setCol(j);
                absoluteLocation.setRow(i);
                absoluteLocation.setSector(sector);
                b.setAbsoluteLocation(absoluteLocation);


                //
//                if (hall.getBooked() != null)
//                    setCellStatus(b, hall.getBooked(), i, j, Status.BOOKED);
//
//                if (hall.getFree() != null)
//                    setCellStatus(b, hall.getFree(), i, j, Status.FREE);
//
//                if (hall.getDisabled() != null)
//                    setCellStatus(b, hall.getDisabled(), i, j, Status.DISABLED);
//
//                if (hall.getBought() != null)
//                    setCellStatus(b, hall.getBought(), i, j, Status.BOUGHT);
                //


                setCellStatus(b, hall.getBooked(), i, j, Status.BOOKED);
                setCellStatus(b, hall.getFree(), i, j, Status.FREE);
                setCellStatus(b, hall.getDisabled(), i, j, Status.DISABLED);
                setCellStatus(b, hall.getBought(), i, j, Status.BOUGHT);


                setCustomCellStatus(b, hall.getCustom(), i, j);


                setSelectedFromList(b);


                tr.addView(b);



            }

            TextView temp2 = new TextView(context);
            temp2.setText(i + "");
            temp2.setLayoutParams(params);
            tr.addView(temp2);

            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


        }


        return tableLayout;

    }


    public void setCellStatus(SitButton cell, Status status) {

        switch (status) {
            case DISABLED:
                cell.getBackground().setColorFilter(Color.parseColor("#FFFAFAFA"), PorterDuff.Mode.MULTIPLY);
                cell.setTextColor(Color.parseColor("#FFFAFAFA"));
                cell.setEnabled(false);
                break;
            case FREE:
                cell.getBackground().setColorFilter(Color.parseColor("#FF4CAF50"), PorterDuff.Mode.MULTIPLY);
                cell.setTextColor(Color.parseColor("#FFFAFAFA"));
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TextView selectedPlacesTextView = ((Activity) context).findViewById(R.id.selectedPlacesTextView);

                        TextView dummyTextView = ((Activity) context).findViewById(R.id.dummyTextView);


                        String buttonContent = cell.getText().toString();
                        if (buttonContent.contains("✓")) {
                            String sitNum = buttonContent.split("✓\n")[1];
                            cell.setText(sitNum);


                            selectedPlaces.remove(sector.charAt(0) + "-" + sitNum);


                            selectedPlacesTextView.setText(getSelectedTextViewContent(selectedPlaces));


                            String dummy = dummyTextView.getText().toString();

                            if (dummy != null && dummy.length() != 0) {

//                                List<SitButton> list = new ArrayList<>();

                                Gson gson = new Gson().newBuilder().create();

                                selectedAbsoluteCells.clear();
                                selectedAbsoluteCells = gson.fromJson(dummy, new TypeToken<List<HallCellAPI>>() {
                                }.getType());



//                                selectedAbsoluteCells.remove(cell.getAbsoluteLocation());
                                int i = getItemFromList(selectedAbsoluteCells, cell.getAbsoluteLocation());

                                selectedAbsoluteCells.remove(i);


                                dummyTextView.setText(gson.toJson(selectedAbsoluteCells));



                            }


                        } else {


                            selectedPlaces.add(sector.charAt(0) + "-" + cell.getText().toString());





                            selectedPlacesTextView.setText(getSelectedTextViewContent(selectedPlaces));


//                            selectedPlaces.add(sector.charAt(0) + "-" + cell.getText().toString());


                            String dummy = dummyTextView.getText().toString();


                            if (dummy != null && dummy.length() != 0) {

//                                List<SitButton> list = new ArrayList<>();

                                Gson gson = new Gson().newBuilder().create();

                                selectedAbsoluteCells = gson.fromJson(dummy, new TypeToken<List<HallCellAPI>>() {
                                }.getType());

                                selectedAbsoluteCells.add(cell.getAbsoluteLocation());

                                dummyTextView.setText(gson.toJson(selectedAbsoluteCells));

                            } else {

//                                List<SitButton> list = new ArrayList<>();

                                Gson gson = new Gson().newBuilder().create();

//                                list.add(cell);
                                selectedAbsoluteCells.add(cell.getAbsoluteLocation());

//                                dummyTextView.setText(gson.toJson(list));
                                dummyTextView.setText(gson.toJson(selectedAbsoluteCells));

                            }
//
                            cell.setText("✓\n" + cell.getText());
                        }
                    }
                });
                break;
            case BOOKED:
                cell.getBackground().setColorFilter(Color.parseColor("#FDD835"), PorterDuff.Mode.MULTIPLY);
                cell.setTextColor(Color.parseColor("#FFFAFAFA"));
                break;
            case BOUGHT:
                cell.getBackground().setColorFilter(Color.parseColor("#F44336"), PorterDuff.Mode.MULTIPLY);
                cell.setTextColor(Color.parseColor("#FFFAFAFA"));
                break;
        }

    }


    public String getSelectedTextViewContent(List<String> content) {
        String temp = "";

        if (content.size() != 0 && content != null) {

            for (String s : content) {
                temp += (s + "; ");
            }


//            if (content.size() == 1) {
//                temp = content.get(0);
//            } else if (content.size() > 1) {
//                for (String s : content) {
//                    temp += (s + "; ");
//                }
//            }

        }


        return temp;
    }


    public void setCellStatus(SitButton button, List<HallCellAPI> cells, int i, int j, Status status) {


        for (HallCellAPI cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();


            if (row == i && col == j) {
                setCellStatus(button, status);
            }

        }

    }


    public void setCustomCellStatus(SitButton button, List<HallCellCustomAPI> cells, int i, int j) {
        for (HallCellCustomAPI cell : cells) {
            int row = cell.getOldRow();
            int col = cell.getOldCol();

            if (row == i && col == j) {

                button.setText((cell.getNewRow()) + "-" + (cell.getNewCol()));
                HallCellAPI relativeLocation = new HallCellAPI();
                relativeLocation.setRow(cell.getNewRow());
                relativeLocation.setCol(cell.getNewCol());

            }
        }
    }


    public int getItemFromList(List<HallCellAPI> list, HallCellAPI hall) {


        for (int i = 0; i < list.size(); i++) {
            HallCellAPI temp = list.get(i);
            if (temp.getCol() == hall.getCol() && temp.getRow() == hall.getRow()) {
                return i;
            }
        }
        return -1;

    }

    private void setSelectedFromList(SitButton cell) {
        for (String s : selectedPlaces) {

            String temp[] = s.split("-");

            if (temp[0].charAt(0) == sector.charAt(0)) {


                String cellContent[] = cell.getText().toString().split("-");


                if (cellContent[0].charAt(0) == temp[1].charAt(0) && cellContent[1].charAt(0) == temp[2].charAt(0)) {

                    //

                    //

                    cell.setText("✓\n" +cell.getText());
//                    cell.setText("✓");
                }


            }

        }

    }

    private void initSelectedPlaces() {
        TextView selectedPlacesTextView = ((Activity) context).findViewById(R.id.selectedPlacesTextView);
        if (selectedPlacesTextView.getText() != null && !selectedPlacesTextView.getText().equals("Please, select place. ")) {

            String[] sits = selectedPlacesTextView.getText().toString().split(";");



            for (String s : sits) {
                if (!s.equals("") && !s.equals(" ")) {
                    selectedPlaces.add(s.trim());

                }
            }


        }
    }


}
