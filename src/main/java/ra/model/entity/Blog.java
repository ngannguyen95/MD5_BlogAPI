package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @Lob
    private String title;
    @Lob
    private String content;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;


    // 1 catalog có nhiều blog
    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "image_blog",joinColumns =@JoinColumn(name = "blogId"),
            inverseJoinColumns =@JoinColumn(name = "imageId"))
    private List<Image> images;

    private boolean blogStatus;
}
