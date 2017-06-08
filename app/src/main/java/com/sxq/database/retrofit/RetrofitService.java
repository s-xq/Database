/*
 *  Copyright(c) 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sxq.database.retrofit;

import com.sxq.database.data.bean.Book;
import com.sxq.database.util.SqlUtil;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by SXQ on 2017/6/8.
 */

public interface RetrofitService {


    @GET(Api.PACKAGE_STATE)
    Observable<Package> getPackageState(@Query("type") String type, @Query("postid") String postId);


    @POST(Api.API_BASE)
    @Headers("Content-type: application/json")
    Observable<List<Book>> getBookList(@Body SqlUtil.PostData postData);

    @GET
    Observable<List<String>> query(String text);
}
