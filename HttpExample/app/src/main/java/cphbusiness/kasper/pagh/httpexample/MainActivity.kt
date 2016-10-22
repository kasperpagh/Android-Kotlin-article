package cphbusiness.kasper.pagh.httpexample

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun sendGet(view: View)
    {
        if (networkChecker())
        {
            Getter(this).execute("https://jsonplaceholder.typicode.com/posts/1")
        }
        else
        {
            Toast.makeText(applicationContext, "WE REQUIRE ADDITIONAL INTERNET! (ie. tænd for nettet din abe!)", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendPost(view: View)
    {
        if (networkChecker())
        {
            Poster(this, DummyJSON("Dette er svaret fra et POST request", "Parset med Gson")).execute("http://jsonplaceholder.typicode.com/posts")
        }
        else
        {
            Toast.makeText(applicationContext, "WE REQUIRE ADDITIONAL INTERNET! (ie. tænd for nettet din abe!)", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendPut(view: View)
    {
        if (networkChecker())
        {
            Putter(this, DummyJSON("Dette er svaret fra et PUT request", "Parset med Gson")).execute("http://jsonplaceholder.typicode.com/posts/1")
        }
        else
        {
            Toast.makeText(applicationContext, "WE REQUIRE ADDITIONAL INTERNET! (ie. tænd for nettet din abe!)", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendDelete(view: View)
    {
        if (networkChecker())
        {
            val textView = this.findViewById(R.id.responseText) as TextView
            textView.text = "Denne her er ikke særlig interessant, tjek vores artikel for svaret på hvordan det gøres (hint: method = DELETE er en udemærket start) \n Eller se det fulde projekt i både Kotllama og Java på github: github.com/kasperpagh/findSelvDetRigtigeRepoLazyBastard"
        }
        else
        {
            Toast.makeText(applicationContext, "WE REQUIRE ADDITIONAL INTERNET! (ie. tænd for nettet din abe!)", Toast.LENGTH_SHORT).show()
        }
    }

    fun networkChecker(): Boolean
    {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        //Denne del er meget vigtig, da netwærk ikke nødvendigvis er et given på mobile devices! Derfor skal man have noget logik der giver en fejl i tilfælde af intet net!
        if (networkInfo != null && networkInfo.isConnected)
        {
            return true
        }
        else
        {
            return false
        }
    }
}
