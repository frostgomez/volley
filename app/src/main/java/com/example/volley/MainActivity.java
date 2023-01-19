package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

   TextView tvOficina, tvCantidad;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GET();

    }
    private void GET(){

        // Instantiate the RequestQueue.
        tvOficina = findViewById(R.id.tvOficinas);
        tvCantidad = findViewById(R.id.tvCantidad);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://elsunnyburguer.000webhostapp.com/api/query_all_total.php?tipo=1";

        Log.d("URL", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("VOLLEY exitosa", response);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            String resultJSON = jsonObject.getString("estado");//Estado es el nombre del campo en el JSON
                            tvOficina.setText(resultJSON);

                            Log.d("JSON", resultJSON.toString());

                            JSONArray contenidoJSON;

                            if (resultJSON.equals("1")){ //hay datos a mostrar

                                contenidoJSON = jsonObject.getJSONArray("oficinas"); //estado es el nombre del campo en el JSON

                                String totales = contenidoJSON.getJSONObject(0).getString("tCantidad");
                                tvCantidad.setText(totales);
                                Log.d("JSON",totales);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", "That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}