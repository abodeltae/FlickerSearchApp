package com.nazeer.flickerproject;

import com.nazeer.flickerproject.DataLayer.JsonProcessors.PhotosListResponseProcessor;
import com.nazeer.flickerproject.DataLayer.models.Photo;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;

import org.json.JSONException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PhotoSearchJunitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testPhotoResponseParsing() throws JSONException {
        String jsonText = "{\"photos\":{\"page\":1,\"pages\":81318,\"perpage\":2,\"total\":\"162636\",\"photo\":[{\"id\":\"46792392625\",\"owner\":\"148157672@N03\",\"secret\":\"61d7a6f271\",\"server\":\"65535\",\"farm\":66,\"title\":\"Amie\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"47708286111\",\"owner\":\"67417750@N06\",\"secret\":\"53263234aa\",\"server\":\"65535\",\"farm\":66,\"title\":\"Thinking...\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"ok\"}";
        Photo photo1 = new Photo("46792392625", "65535", 66, "61d7a6f271");
        Photo photo2 = new Photo("47708286111", "65535", 66, "53263234aa");
        List<Photo> photos = Arrays.asList(photo1, photo2);
        PhotoListResponse response = new PhotoListResponse(81318, 1, photos);
        PhotoListResponse parsed = new PhotosListResponseProcessor().process(jsonText);
        assertEquals(response, parsed);
    }

}
