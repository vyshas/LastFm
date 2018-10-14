package com.example.testlastfm.api;

import static com.example.testlastfm.util.LiveDataTestUtil.getValue;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.testlastfm.model.Album;
import com.example.testlastfm.model.AlbumResults;
import com.example.testlastfm.model.AlbumSearchResponse;
import com.example.testlastfm.model.Artist;
import com.example.testlastfm.model.ArtistResults;
import com.example.testlastfm.model.ArtistsSearchResponse;
import com.example.testlastfm.util.LiveDataCallAdapterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(JUnit4.class)
public class LastFmServiceTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private LastFmService service;

    private MockWebServer mockWebServer;

    @Before
    public void createService() throws IOException {
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(LastFmService.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.<String, String>emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api-response/" + fileName);

        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)));
    }

    @Test
    public void getArtistResults() throws IOException,InterruptedException{
        enqueueResponse("artists-shakira.json");
        ArtistsSearchResponse artistsSearchResponse = getValue(service.getArtist("shakira","cbadaf15a81562eba0aafc0e4062e6e1")).body;

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(),is("/?method=artist.search&format=json&artist=shakira&api_key=cbadaf15a81562eba0aafc0e4062e6e1"));
        assertThat(artistsSearchResponse,notNullValue());

        ArtistResults artistResults = artistsSearchResponse.getResults();
        assertThat(artistResults,notNullValue());

        List<Artist> artistList = artistResults.getArtistMatches().getArtist();
        assertThat(artistList.size(),is(30));


    }

    @Test
    public void getAlbumResults() throws IOException,InterruptedException{
        enqueueResponse("album-believe.json");
        AlbumSearchResponse albumSearchResponse = getValue(service.getAlbum("believe", "cbadaf15a81562eba0aafc0e4062e6e1")).body;

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(),is("/?method=album.search&format=json&album=believe&api_key=cbadaf15a81562eba0aafc0e4062e6e1"));
        assertThat(albumSearchResponse,notNullValue());

        AlbumResults albumResults = albumSearchResponse.getAlbumResults();
        assertThat(albumResults,notNullValue());
        assertThat(albumResults.getOpensearchTotalResults(),is("113802"));

        List<Album> albumList = albumResults.getAlbummatches().getAlbum();
        assertThat(albumList.size(),is(50));
        assertThat(albumList.get(0).getImage().get(0),notNullValue());


    }

}
