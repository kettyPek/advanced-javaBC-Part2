package ajbc.learn.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"picture"})
@NoArgsConstructor
public class Category {
	
	private int categoryId;
	private String categoryName;
	private String description;
	private byte[] picture;

}
