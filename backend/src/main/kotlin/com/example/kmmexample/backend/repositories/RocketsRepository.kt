package com.example.kmmexample.backend.repositories

import com.example.kmmexample.data.database.LaunchesQueries
import com.example.kmmexample.data.database.RocketQueries
import com.example.kmmexample.data.models.Links
import com.example.kmmexample.data.models.Rocket
import com.example.kmmexample.data.models.RocketLaunch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RocketsRepository(
    private val launchesQueries: LaunchesQueries,
    private val rocketQueries: RocketQueries
) {
    suspend fun getLaunches(): List<RocketLaunch> = withContext(Dispatchers.Unconfined) {
        launchesQueries.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        launchYear: Int,
        rocketId: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        missionPatchUrl: String?,
        articleUrl: String?,
        rocket_id: String?,
        name: String?,
        type: String?
    ) =
        RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            launchYear = launchYear,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            rocket = Rocket(
                id = rocketId,
                name = name!!,
                type = type!!
            ),
            links = Links(
                missionPatchUrl = missionPatchUrl,
                articleUrl = articleUrl
            )
        )
}