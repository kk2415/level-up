// import React from 'react';
// import {Container} from 'react-bootstrap'
// import { ChannelTable } from './channel/ChannelTable'
// import Pager from './pager/Pager'
//
// const Board = () => {
//     let article = []
//
//     return (
//         <Container>
//             <div className="row">
//                 <div className="col-lg-6 col-sm-12 text-lg-start text-center">
//                 </div>
//                 <div className="col-lg-6 col-sm-12 text-lg-end text-center">
//                     <nav className="navbar navbar-expand-lg navbar-light float-end">
//                         <div className="container-fluid">
//                             <div className="collapse navbar-collapse" id="navbarSupportedContent">
//                                 <ul className="navbar-nav d-flex">
//                                     <li className="nav-item dropdown">
//                                         <select name="field" className="nav-link dropdown-toggle" href="#"
//                                                 id="navbarDropdown"
//                                                 role="button" data-bs-toggle="dropdown" aria-expanded="false">
//                                             <option value="title">제목</option>
//                                             <option value="writer">작성자</option>
//                                         </select>
//                                     </li>
//                                 </ul>
//                                 <form className="d-flex">
//                                     <input className="form-control me-2" type="search" placeholder="Search"
//                                            aria-label="Search" id="search" value="" />
//                                     <button className="btn btn-outline-success" type="button" id="searchButton">
//                                         Search
//                                     </button>
//                                 </form>
//                             </div>
//                         </div>
//                     </nav>
//                 </div>
//             </div>
//
//             <ChannelTable articleInfo={article} />
//
//             <div className="row">
//                 <div className="col-lg-6 col-sm-12 text-lg-start text-center">
//                     <button type="button" className="btn btn-primary btn-sm" id="backButton">홈으로</button>
//                 </div>
//                 <div className="col-lg-6 col-sm-12 text-lg-end text-center">
//                     <button type="button" className="btn btn-primary btn-sm" id="postingButton">글쓰기</button>
//                 </div>
//             </div>
//
//             <br/><br/>
//
//             <Pager />
//
//         </Container>
//     );
// };
//
// export default Board;
