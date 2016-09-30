package com.callippus.web.dao.training;

import java.util.ArrayList;

import com.callippus.web.beans.training.TrainingBean;

public interface ITrainingDAO {

	public String saveTrainingDetails(TrainingBean training) throws Exception;

	public ArrayList getTrainingDetails(TrainingBean training) throws Exception;

	public String deleteTrainingDetails(TrainingBean training) throws Exception;

}
