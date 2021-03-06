package org.fossasia.susi.ai.data

import org.fossasia.susi.ai.data.contract.IGroupWiseSkillsModel
import org.fossasia.susi.ai.dataclasses.SkillsListQuery
import org.fossasia.susi.ai.rest.ClientBuilder
import org.fossasia.susi.ai.rest.responses.susi.ListSkillsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 *
 * Created by arundhati24 on 16/07/2018.
 */
class GroupWiseSkillsModel : IGroupWiseSkillsModel {

    private lateinit var authResponseCallSkills: Call<ListSkillsResponse>

    override fun fetchSkills(group: String, language: String, listener: IGroupWiseSkillsModel.OnFetchSkillsFinishedListener) {
        val queryObject = SkillsListQuery(group, language, "true", "descending", "rating")
        authResponseCallSkills = ClientBuilder.fetchListSkillsCall(queryObject)

        authResponseCallSkills.enqueue(object : Callback<ListSkillsResponse> {
            override fun onResponse(call: Call<ListSkillsResponse>, response: Response<ListSkillsResponse>) {
                listener.onSkillFetchSuccess(response, group)
            }

            override fun onFailure(call: Call<ListSkillsResponse>, t: Throwable) {
                Timber.e(t)
                listener.onSkillFetchFailure(t)
            }
        })
    }

    override fun cancelFetch() {
        try {
            authResponseCallSkills.cancel()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}