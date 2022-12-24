package com.girish.a7minworkout

object Constants {
   fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList=ArrayList<ExerciseModel>()
        val jumping=ExerciseModel(1,"push up",R.drawable.ic_push_up_and_rotation,false,false)
        exerciseList.add(jumping)
        val abdominal=ExerciseModel(2,"abdominal crunches",R.drawable.ic_abdominal_crunch,false,false)
        exerciseList.add(abdominal)
        val knees=ExerciseModel(3,"high knee running",R.drawable.ic_high_knees_running_in_place,false,false)
        exerciseList.add(knees)
        val lunge=ExerciseModel(4,"lunge",R.drawable.ic_lunge,false,false)
        exerciseList.add(lunge)
        val plang=ExerciseModel(5,"plank",R.drawable.ic_plank,false,false)
        exerciseList.add(plang)
        val squats=ExerciseModel(6,"squats",R.drawable.ic_squat,false,false)
        exerciseList.add(squats)
        val step=ExerciseModel(7,"step up",R.drawable.ic_step_up_onto_chair,false,false)
        exerciseList.add(step)
        return exerciseList
    }
}