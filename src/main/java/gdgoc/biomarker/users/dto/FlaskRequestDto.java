package gdgoc.biomarker.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlaskRequestDto {
    private double height;
    private double weight;
    private double body_fat_percentage; // 체지방률
    private double skeletal_muscle_mass; // 골격근량
    private double bmr; // 기초대사량
    private double body_fat_mass; //체지방량
    private String goal; // 목적
    private String gender;

    //생성자 추가
    public FlaskRequestDto(double height, double weight, double body_fat_percentage,
                           double skeletal_muscle_mass, double bmr, double body_fat_mass,
                           String goal, String gender) {
        this.height = height;
        this.weight = weight;
        this.body_fat_percentage = body_fat_percentage;
        this.skeletal_muscle_mass = skeletal_muscle_mass;
        this.bmr = bmr;
        this.body_fat_mass = body_fat_mass;
        this.goal = goal;
        this.gender = gender;
    }
}
