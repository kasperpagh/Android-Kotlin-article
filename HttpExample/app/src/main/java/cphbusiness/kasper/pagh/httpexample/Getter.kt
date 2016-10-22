package cphbusiness.kasper.pagh.httpexample

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by kaspe on 2016-10-22.
 */

class Getter(internal var app: AppCompatActivity)//        this.view = view;
//        this.context = context;
: AsyncTask<String, Void, String>() {

    internal var view: View? = null
    internal var context: Context? = null
    override fun doInBackground(vararg urls: String): String {
        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0])
        } catch (e: IOException) {
            Toast.makeText(app.applicationContext, "Unable to complete request (IOException)", Toast.LENGTH_SHORT).show()
            return "Unable to retrieve web page. URL may be invalid."
        }

    }

    override fun onPostExecute(result: String) {
        val textView = app.findViewById(R.id.responseText) as TextView
        textView.text = result
    }

    @Throws(IOException::class)
    private fun downloadUrl(myurl: String): String {
        var `is`: InputStream? = null
        // Only display the first 500 characters of the retrieved
        // web page content.
        val len = 500

        try {
            val url = URL(myurl)
            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 10000
            conn.connectTimeout = 15000
            conn.requestMethod = "GET"
            conn.doInput = true
            // Starts the query
            conn.connect()
            val response = conn.responseCode
            `is` = conn.inputStream

            // Convert the InputStream into a string
            val contentAsString = readIt(`is`, len)
            return contentAsString

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (`is` != null) {
                `is`.close()
            }
        }
    }

    @Throws(IOException::class, UnsupportedEncodingException::class)
    fun readIt(stream: InputStream, len: Int): String {
        var reader: Reader? = null
        reader = InputStreamReader(stream, "UTF-8")
        val buffer = CharArray(len)
        reader.read(buffer)
        return String(buffer)
    }

}
