package com.cloudx.common.core.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户UserAgent
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 16:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAgent implements Serializable {

  private static final long serialVersionUID = 3264203587373340972L;

  private String browser;

  private String system;

}
