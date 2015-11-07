package webarch.aaruush15.TeamFragments;



import java.util.ArrayList;
import java.util.List;

import webarch.aaruush15.R;


public class UtilsPatrons {
    public static final List<MemberModel> patrons = new ArrayList<>();

    static {
        patrons.add(new MemberModel(R.drawable.director, "Dr C. Muthamizhchelvan", R.color.sienna, "Director E & T","Patron","director.et@srmuniv.ac.in"));
        patrons.add(new MemberModel(R.drawable.convenor, "Prof. Rathinam. A", R.color.saffron, "Convenor - Aaruush","Patron","convenor@aaruush.net"));
        patrons.add(new MemberModel(R.drawable.fa, "Prof. V. Ponniah", R.color.green, "Finance Advisor - Aaruush","Patron","advisor.finance@aaruush.net"));
        patrons.add(new MemberModel(R.drawable.eo, "Mr. V. Thirumurugan", R.color.pink, "Estate Officer","Patron","estate.officer@srmuniv.ac.in"));
    }
}
