package com.musnadil.challengechapter5.ui.detailnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.ui.theme.ChallengeChapter5Theme
import com.musnadil.challengechapter5.ui.theme.yellow
import com.skydoves.landscapist.glide.GlideImage


class DetailNewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            val urlImage = arguments?.getString("img")
            val title = arguments?.getString("title")
            val sourceName = arguments?.getString("publisher")
            val publishedAt = arguments?.getString("time_published")
            val content = arguments?.getString(
                "content",
                "Tidak ada konten yang di tampilkan, silahkan klik tumbol kunjungi laman berita untuk melihat detail berita"
            )
            val urlLaman = arguments?.getString("url_laman")
            setContent {
                ChallengeChapter5Theme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Column {
                            Detail(
                                urlImage.toString(),
                                title.toString(),
                                sourceName.toString(),
                                publishedAt.toString(),
                                content.toString(),
                            )
                            ReadNews(urlLaman.toString())
                        }
                    }
                }
            }
        }
    }

    private val poppinsFamily = FontFamily(
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_semibold, FontWeight.Medium)
    )

    @Composable
    fun Detail(
        urlImage: String, title: String, sourceName: String,
        publishedAt: String, content: String
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Log.d("inigambar", urlImage)
            Spacer(modifier = Modifier.height(48.dp))
            GlideImage(
                imageModel = urlImage,
                placeHolder = ImageBitmap.imageResource(R.drawable.default_image),
                error = ImageBitmap.imageResource(R.drawable.default_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(250.dp)
            )
            Text(
                text = title,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            Text(
                text = sourceName,
                textAlign = TextAlign.Left,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            Text(
                text = publishedAt,
                textAlign = TextAlign.Left,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            Text(
                text = content,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }
    }

    @Composable
    fun ReadNews(urlLaman: String) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(
                onClick = {
                    val bundle = Bundle().apply {
                        putString("url", urlLaman)
                    }
                    findNavController().navigate(
                        R.id.action_detailNewsFragment_to_webViewFragment,
                        bundle
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = yellow),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Baca Berita Lengkap",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                    )
                )
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        ChallengeChapter5Theme {
            Column {
                Detail(
                    "https://statik.tempo.co/?id=740517&width=650",
                    "Jangan Diabaikan, Gejala Kolesterol Tinggi Bisa Dilihat dari Mata - Cantika.com",
                    "Cantika.com",
                    "2022-06-07T06:05:00Z",
                    "CANTIKA.COM, Jakarta - Kolesterol tinggi bisa disebabkan karena Anda kurang berolahraga, sehingga terjadi penumpukkan kolesterol di arteri. Penumpukan lemak terjadi karena terlalu banyak mengonsumsi … [+1833 chars]",
                )
                ReadNews(urlLaman = "")
            }
        }
    }
}